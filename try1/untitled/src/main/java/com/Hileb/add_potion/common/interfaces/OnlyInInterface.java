package com.Hileb.add_potion.common.interfaces;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnlyInInterface extends TranslateInterfaceItemHandler{

    public OnlyInInterface(IItemHandler handler, List<Integer> slotIn) {
        super(handler, slotIn);
    }

    @Override
    public boolean canOut() {
        return false;
    }

    @Override
    public boolean canIn() {
        return true;
    }
}
