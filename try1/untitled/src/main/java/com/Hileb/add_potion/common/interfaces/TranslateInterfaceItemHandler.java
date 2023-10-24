package com.Hileb.add_potion.common.interfaces;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class TranslateInterfaceItemHandler implements IItemHandler {
    private final IItemHandler inventory;
    private final List<Integer> slotId;
    public TranslateInterfaceItemHandler(IItemHandler handler, List<Integer> slotIn){
        slotId=slotIn;
        inventory=handler;
    }
    @Override
    public int getSlots() {
        return slotId.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slotId.get(slot));
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (canOut())return inventory.insertItem(slotId.get(slot),stack,simulate);
        else return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (canIn())return inventory.extractItem(slotId.get(slot),amount,simulate);
        else return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.getSlotLimit(slotId.get(slot));
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return inventory.isItemValid(slotId.get(slot),stack);
    }
    public abstract boolean canOut();
    public abstract boolean canIn();
}
