package com.Hileb.add_potion.api.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class APPotionAffectEvent extends LivingEvent {
	protected final ItemStack food;
	public APPotionAffectEvent(LivingEntity player, ItemStack food) {
		super(player);
		this.food = food;
	}

	public ItemStack getFood() {
		return this.food;
	}

	@Override
	public boolean isCancelable() {
		return true;
	}
}
