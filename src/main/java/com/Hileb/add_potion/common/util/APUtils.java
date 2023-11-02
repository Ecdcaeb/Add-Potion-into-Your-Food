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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

import static com.Hileb.add_potion.IdlFramework.MODID;

public final class APUtils {
	private static final String TAG_EFFECTS = new ResourceLocation(MODID, "effects").toString();

	public static boolean canPlaceToPotionSlot(ItemStack itemStack) {
		boolean ret = itemStack.getItem() instanceof PotionItem || LoadMods.isBotaniaPotion(itemStack);
		IngredientCheckEvent event = new IngredientCheckEvent.Potion(itemStack, ret);
		MinecraftForge.EVENT_BUS.post(event);
		return event.isIngredient();
	}

	public static boolean canPlaceToFoodSlot(ItemStack itemStack) {
		IngredientCheckEvent event = new IngredientCheckEvent.Food(itemStack, itemStack.isEdible());
		MinecraftForge.EVENT_BUS.post(event);
		return event.isIngredient();
	}

	public static List<MobEffectInstance> getPotionEffects(ItemStack itemStack) {
		List<MobEffectInstance> ret = Lists.newArrayList();
		ret.addAll(PotionUtils.getMobEffects(itemStack));
		ret.addAll(LoadMods.getBotaniaEffects(itemStack));
		PotionEffectEvent event = new PotionEffectEvent(itemStack, ret);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getEffects();
	}

	public static ItemStack applyEffectsToFood(ItemStack food, List<MobEffectInstance> effects) {
		ItemStack ret = food.copy();
		ApplyEffectsToFoodEvent event = new ApplyEffectsToFoodEvent(ret, effects);
		MinecraftForge.EVENT_BUS.post(event);
		CompoundTag nbt = ret.getOrCreateTag();
		ListTag listTag;
		if(nbt.contains(TAG_EFFECTS, Tag.TAG_LIST)) {
			listTag = nbt.getList(TAG_EFFECTS, Tag.TAG_COMPOUND);
		} else {
			listTag = new ListTag();
		}
		for(MobEffectInstance instance: event.getEffects()) {
			CompoundTag tag = new CompoundTag();
			instance.save(tag);
			
			listTag.add(tag);
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
}
