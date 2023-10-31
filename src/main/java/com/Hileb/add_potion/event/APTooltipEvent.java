package com.Hileb.add_potion.event;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.List;

public class APTooltipEvent extends Event {
	public List<String> stringList;
	public ItemStack stack;
	public APTooltipEvent(@Nonnull List<String> tooltip, ItemStack stackIn) {
		stringList=tooltip;
		stack=stackIn;
	}

	@Override
	public boolean isCancelable() {
		return false;
	}
}
