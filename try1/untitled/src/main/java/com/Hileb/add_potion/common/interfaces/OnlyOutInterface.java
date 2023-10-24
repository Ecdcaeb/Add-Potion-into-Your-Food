package com.Hileb.add_potion.common.interfaces;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnlyOutInterface extends TranslateInterfaceItemHandler{

    public OnlyOutInterface(IItemHandler handler, List<Integer> slotIn) {
        super(handler, slotIn);
    }

    @Override
    public boolean canOut() {
        return true;
    }

    @Override
    public boolean canIn() {
        return false;
    }
}
