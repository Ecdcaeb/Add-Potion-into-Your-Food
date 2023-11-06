package com.Hileb.add_potion.common.util;

import com.Hileb.add_potion.api.event.APItemPotionTypeEvent;
import com.Hileb.add_potion.api.event.ApplyEffectsToFoodEvent;
import com.Hileb.add_potion.api.event.IngredientCheckEvent;
import com.Hileb.add_potion.api.event.PotionEffectEvent;
import com.Hileb.add_potion.common.util.compat.LoadMods;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SplashPotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.Hileb.add_potion.AddPotion.MODID;

public final class APUtils {
	public static final String TAG_EFFECTS = new ResourceLocation(MODID, "effects").toString();
	public static final String TAG_DISABLE = new ResourceLocation(MODID, "disable").toString();
	public static final String TAG_OWNER = new ResourceLocation(MODID, "owner").toString();
	private static final String TAG_POTION_TYPE = new ResourceLocation(MODID, "potion_type").toString();

	public enum PotionType implements IExtensibleEnum {
		DEFAULT((livingEntity, effect, owner) -> {
			MobEffect mobEffect = effect.getEffect();
			if(mobEffect.isInstantenous()) {
				mobEffect.applyInstantenousEffect(owner, owner, livingEntity, effect.getAmplifier(), 1.0D);
			} else {
				livingEntity.addEffect(effect, owner);
			}
		}),
		SPLASH((livingEntity, effect, owner) -> {
			livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
			AABB aabb = AABB.ofSize(livingEntity.position(), 8.0, 4.0, 8.0);
			List<LivingEntity> list = livingEntity.level.getEntitiesOfClass(LivingEntity.class, aabb);
			for(LivingEntity victim: list) {
				if (victim.isAffectedByPotions()) {
					double dist = livingEntity.distanceToSqr(victim);
					if (dist < 16.0D) {
						double multiplier = 1.0D - Math.sqrt(dist) / 4.0D;
						if (victim == livingEntity) {
							multiplier = 1.0D;
						}

						MobEffect mobEffect = effect.getEffect();
						if (mobEffect.isInstantenous()) {
							mobEffect.applyInstantenousEffect(owner, owner, victim, effect.getAmplifier(), multiplier);
						} else {
							int i = (int)(multiplier * (double)effect.getDuration() + 0.5D);
							if (i > 20) {
								victim.addEffect(new MobEffectInstance(mobEffect, i, effect.getAmplifier(), effect.isAmbient(), effect.isVisible()), owner);
							}
						}
					}
				}
			}
		}),
		LINGERING((livingEntity, effect, owner) -> {
			livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
			AreaEffectCloud cloud = new AreaEffectCloud(livingEntity.level, livingEntity.getX(), livingEntity.getY(0.25D), livingEntity.getZ());
			cloud.setOwner(owner);
			cloud.setRadius(3.0F);
			cloud.setRadiusOnUse(-0.5F);
			cloud.setWaitTime(10);
			cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
			cloud.addEffect(effect);
			livingEntity.level.addFreshEntity(cloud);
		});

		public final TriConsumer<LivingEntity, MobEffectInstance, LivingEntity> afterEat;	//Call this on server side only!

		PotionType(TriConsumer<LivingEntity, MobEffectInstance, LivingEntity> afterEat) {
			this.afterEat = afterEat;
		}

		@SuppressWarnings("unused")
		public static PotionType create(String name, TriConsumer<LivingEntity, MobEffectInstance, LivingEntity> afterEat) {
			throw new IllegalStateException("Enum not extended");
		}
	}

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

	public static void applyEffectTo(ListTag listTag, MobEffectInstance instance, PotionType potionType) {
		CompoundTag tag = new CompoundTag();
		instance.save(tag);
		setPotionType(tag, potionType);

		listTag.add(tag);
	}

	public static Tuple<ItemStack, Optional<ItemStack>> applyEffectsToFood(@Nullable LivingEntity owner, ItemStack potion, ItemStack food) {
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
		PotionType potionType = PotionType.DEFAULT;
		if(potion.getItem() instanceof PotionItem potionItem) {
			potionType = getPotionTypeOfPotionItem(potionItem);
		}
		ApplyEffectsToFoodEvent event = new ApplyEffectsToFoodEvent(potion, ret, effects);
		MinecraftForge.EVENT_BUS.post(event);
		for(MobEffectInstance instance: event.getEffects()) {
			applyEffectTo(listTag, instance, potionType);
		}
		if(owner != null) {
			nbt.putUUID(TAG_OWNER, owner.getUUID());
		}
		nbt.put(TAG_EFFECTS, listTag);
		ret.setTag(nbt);

		return new Tuple<>(ret, Optional.ofNullable(event.getPotionRemaining()));
	}
	
	public static Map<MobEffectInstance, PotionType> getEffectsFromFood(ItemStack food) {
		Map<MobEffectInstance, PotionType> ret = Maps.newHashMap();
		CompoundTag nbt = food.getTag();
		if(nbt != null && nbt.contains(TAG_EFFECTS, Tag.TAG_LIST)) {
			ListTag listTag = nbt.getList(TAG_EFFECTS, Tag.TAG_COMPOUND);
			listTag.forEach(tag -> {
				CompoundTag compoundTag = (CompoundTag)tag;
				MobEffectInstance instance = MobEffectInstance.load(compoundTag);
				PotionType potionType = getPotionType(compoundTag);
				if(instance != null) {
					ret.put(instance, potionType);
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

	public static PotionType getPotionType(CompoundTag nbt) {
		if(nbt.contains(TAG_POTION_TYPE, Tag.TAG_STRING)) {
			try {
				return PotionType.valueOf(nbt.getString(TAG_POTION_TYPE));
			} catch (IllegalArgumentException ignored) {
			}
		}
		return PotionType.DEFAULT;
	}

	public static void setPotionType(CompoundTag nbt, PotionType potionType) {
		nbt.putString(TAG_POTION_TYPE, potionType.name());
	}

	public static PotionType getPotionTypeOfPotionItem(PotionItem potionItem) {
		if(potionItem instanceof SplashPotionItem) {
			return PotionType.SPLASH;
		}
		if(potionItem instanceof LingeringPotionItem) {
			return PotionType.LINGERING;
		}
		APItemPotionTypeEvent event = new APItemPotionTypeEvent(potionItem);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getPotionType();
	}
}
