package com.Hileb.add_potion.event;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class APApplyEvent extends Event {
	public ItemStack stack;
	public EnumApply applyResult=EnumApply.PASS;
	public enum  EnumApply {
		PASS,
		APLLY,
		FALSE;
	}
	@Override
	public boolean isCancelable() {
		return false;
	}
	public static class Food extends APApplyEvent{
		public Food(ItemStack stackIn) {stack=stackIn;}

	}
	public static class Potion extends APApplyEvent{
		public Potion(ItemStack stackIn) {stack=stackIn;}
	}
}
