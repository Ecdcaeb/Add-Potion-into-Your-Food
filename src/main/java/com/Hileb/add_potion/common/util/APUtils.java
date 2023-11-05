package com.Hileb.add_potion.common.util;

import com.Hileb.add_potion.api.event.ApplyEffectsToFoodEvent;
import com.Hileb.add_potion.api.event.IngredientCheckEvent;
import com.Hileb.add_potion.api.event.PotionEffectEvent;
import com.Hileb.add_potion.common.util.compat.LoadMods;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.List;

import static com.Hileb.add_potion.AddPotion.MODID;

public final class APUtils {
	public static final String TAG_EFFECTS = new ResourceLocation(MODID, "effects").toString();
	public static final String TAG_DISABLE = new ResourceLocation(MODID, "disable").toString();
	private static final String TAG_OWNER = new ResourceLocation(MODID, "owner").toString();

	public static boolean canPlaceToPotionSlot(ItemStack potion) {
		boolean ret = potion.getItem() instanceof PotionItem || LoadMods.isBotaniaPotion(potion);
		IngredientCheckEvent event = new IngredientCheckEvent.Potion(potion, ret);
		MinecraftForge.EVENT_BUS.post(event);
		return event.isIngredient();
	}

	public static boolean canPlaceToFoodSlot(ItemStack food) {
		IngredientCheckEvent event = new IngredientCheckEvent.Food(food, food.isEdible());
		MinecraftForge.EVENT_BUS.post(event);
		return event.isIngredient();
	}

	public static List<MobEffectInstance> getPotionEffects(ItemStack potion) {
		List<MobEffectInstance> ret = Lists.newArrayList();
		ret.addAll(PotionUtils.getMobEffects(potion));
		ret.addAll(LoadMods.getBotaniaEffects(potion));
		PotionEffectEvent event = new PotionEffectEvent(potion, ret);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getEffects();
	}

	public static void applyEffectTo(ListTag listTag, MobEffectInstance instance) {
		CompoundTag tag = new CompoundTag();
		instance.save(tag);

		listTag.add(tag);
	}

	public static ItemStack applyEffectsToFood(@Nullable LivingEntity owner, ItemStack potion, ItemStack food) {
		List<MobEffectInstance> effects = getPotionEffects(potion);
		ItemStack ret = food.copy();
		ret.setCount(1);
		setEffectsShow(ret);
		CompoundTag nbt = ret.getOrCreateTag();
		ListTag listTag;
		if(nbt.contains(TAG_EFFECTS, Tag.TAG_LIST)) {
			listTag = nbt.getList(TAG_EFFECTS, Tag.TAG_COMPOUND);
		} else {
			listTag = new ListTag();
		}
		ApplyEffectsToFoodEvent event = new ApplyEffectsToFoodEvent(potion, ret, effects);
		MinecraftForge.EVENT_BUS.post(event);
		for(MobEffectInstance instance: event.getEffects()) {
			applyEffectTo(listTag, instance);
		}
		if(owner != null) {
			nbt.putUUID(TAG_OWNER, owner.getUUID());
		}
		nbt.put(TAG_EFFECTS, listTag);
		ret.setTag(nbt);

		return ret;
	}
	
	public static List<MobEffectInstance> getEffectsFromFood(ItemStack food) {
		List<MobEffectInstance> ret = Lists.newArrayList();
		CompoundTag nbt = food.getTag();
		if(nbt != null && nbt.contains(TAG_EFFECTS, Tag.TAG_LIST)) {
			ListTag listTag = nbt.getList(TAG_EFFECTS, Tag.TAG_COMPOUND);
			listTag.forEach(tag -> {
				MobEffectInstance instance = MobEffectInstance.load((CompoundTag) tag);
				if(instance != null) {
					ret.add(instance);
				}
			});
		}
		return ret;
	}

	@Nullable
	public static LivingEntity getOwner(ServerLevel level, ItemStack food) {
		CompoundTag nbt = food.getTag();
		if(nbt != null && nbt.contains(TAG_OWNER, Tag.TAG_INT_ARRAY)) {
			Entity entity = level.getEntity(nbt.getUUID(TAG_OWNER));
			return entity instanceof LivingEntity livingEntity ? livingEntity : null;
		}
		return null;
	}

	@OnlyIn(Dist.CLIENT)
	public static boolean isEffectsHiding(ItemStack food) {
		CompoundTag nbt = food.getTag();
		return nbt != null && nbt.contains(TAG_DISABLE, Tag.TAG_BYTE) && nbt.getBoolean(TAG_DISABLE);
	}

	public static void setEffectsHiding(ItemStack food) {
		CompoundTag tag = food.getOrCreateTag();
		tag.putBoolean(TAG_DISABLE, true);
		food.setTag(tag);
	}

	public static void setEffectsShow(ItemStack food) {
		CompoundTag tag = food.getOrCreateTag();
		tag.remove(TAG_DISABLE);
		if(tag.isEmpty()) {
			tag = null;
		}
		food.setTag(tag);
	}
}
