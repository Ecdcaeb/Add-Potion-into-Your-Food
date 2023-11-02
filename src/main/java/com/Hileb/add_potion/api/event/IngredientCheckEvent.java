package com.Hileb.add_potion.api.event;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * This Event is fired to indicating if an item is food or potion. Fired in Forge bus (MinecraftForge.EVENT_BUS), and is NOT cancelable.
 */
public abstract class IngredientCheckEvent extends Event {
	protected ItemStack stack;

	protected boolean modified;

	protected boolean ingredient;

	protected IngredientCheckEvent(ItemStack stack, boolean ingredient) {
		this.stack = stack;
		this.modified = false;
		this.ingredient = ingredient;
	}

	public ItemStack getStack() {
		return this.stack;
	}

	public boolean isIngredient() {
		return this.ingredient;
	}

	/**
	 * @param ingredient	Set this item to ingredient (food / potion) or not.
	 */
	public void setIngredient(boolean ingredient) {
		this.ingredient = ingredient;
		this.modified = true;
	}

	public boolean isModified() {
		return this.modified;
	}

	public static class Food extends IngredientCheckEvent {
		public Food(ItemStack stack, boolean ingredient) {
			super(stack, ingredient);
		}

	}

	/**
	 * Remember to subscribe PotionEffectEvent.
	 * @see PotionEffectEvent
	 */
	public static class Potion extends IngredientCheckEvent {
		public Potion(ItemStack stack, boolean ingredient) {
			super(stack, ingredient);
		}
	}
}
