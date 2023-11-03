package com.Hileb.add_potion.api.event;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

/**
 * This Event is fired to indicating the effects of a potion item.
 * @see IngredientCheckEvent.Potion
 */
public class PotionEffectEvent extends Event {
	protected final ItemStack stack;
	protected final List<MobEffectInstance> effects;

	protected boolean modified;

	public PotionEffectEvent(ItemStack stack, List<MobEffectInstance> effects) {
		this.stack = stack;
		this.effects = effects;
		this.modified = false;
	}

	public boolean isModified() {
		return this.modified;
	}

	public void addEffect(MobEffectInstance effect) {
		for(int i = 0; i < this.effects.size(); ++i) {
			MobEffectInstance current = this.effects.get(i);
			if(current.getEffect().equals(effect.getEffect()) &&
					(current.getAmplifier() < effect.getAmplifier() ||
							(current.getAmplifier() == effect.getAmplifier() && current.getDuration() < effect.getDuration()))) {
				this.effects.set(i, effect);
				return;
			}
		}
		this.effects.add(effect);
	}

	public boolean removeEffect(MobEffect effect) {
		return this.effects.removeIf(effectInstance -> effectInstance.getEffect().equals(effect));
	}

	public List<MobEffectInstance> getEffects() {
		return this.effects;
	}
}
