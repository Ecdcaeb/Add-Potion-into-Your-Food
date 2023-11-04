package com.Hileb.add_potion.api.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * This Event is fired when player uses a potion table to add potion to food.
 * @see com.Hileb.add_potion.common.gui.PotionTableMenu#clickMenuButton
 */
public class APCraftEvent extends PlayerEvent {
	protected final ItemStack potion;
	protected final ItemStack food;
	protected ItemStack result;

	public APCraftEvent(Player player, ItemStack potion, ItemStack food, ItemStack result) {
		super(player);
		this.potion = potion;
		this.food = food;
		this.result = result;
	}


	@Override
	public boolean isCancelable() {
		return false;
	}

	public ItemStack getPotion() {
		return this.potion;
	}

	public ItemStack getFood() {
		return this.food;
	}

	public ItemStack getOutput() {
		return this.result;
	}

	public void setOutput(ItemStack result) {
		this.result = result;
	}
}
