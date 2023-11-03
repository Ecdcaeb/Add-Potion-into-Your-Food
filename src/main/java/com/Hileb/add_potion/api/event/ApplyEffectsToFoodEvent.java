package com.Hileb.add_potion.api.event;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

/**
 * This Event is fired when applying effects to food.
 * @see com.Hileb.add_potion.common.util.APUtils#applyEffectsToFood
 */
public class ApplyEffectsToFoodEvent extends Event {
	protected final ItemStack potion;
	protected final ItemStack food;
	protected final List<MobEffectInstance> effects;

	public ApplyEffectsToFoodEvent(ItemStack potion, ItemStack food, List<MobEffectInstance> effects) {
		this.potion = potion;
		this.food = food;
		this.effects = effects;
	}

	public ItemStack getPotion() {
		return this.potion;
	}

	public ItemStack getFood() {
		return this.food;
	}

	public boolean contains(MobEffect effect) {
		return this.effects.stream().anyMatch(instance -> instance.getEffect().equals(effect));
	}

	public boolean contains(MobEffect effect, int amplifier) {
		return this.effects.stream().anyMatch(instance -> instance.getEffect().equals(effect) && instance.getAmplifier() == amplifier);
	}

	public List<MobEffectInstance> getEffects() {
		return this.effects;
	}
}
