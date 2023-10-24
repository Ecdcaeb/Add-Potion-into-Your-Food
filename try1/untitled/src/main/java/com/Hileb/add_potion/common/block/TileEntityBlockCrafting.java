package com.Hileb.add_potion.common.block;


import com.Hileb.add_potion.AddPotionMain;
import com.Hileb.add_potion.common.event.APCraftEvent;
import com.Hileb.add_potion.common.init.ModConfig;
import com.Hileb.add_potion.common.interfaces.OnlyInInterface;
import com.Hileb.add_potion.common.interfaces.OnlyOutInterface;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.common.potion.PotionProcess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBlockCrafting extends BlockEntity  {
    public static final String NBT_INVENTORY="Inventory";

    public final ItemStackHandler inventory=new ItemStackHandler(3){
        @Override
        public int getSlotLimit(int slot) {
            if (ModConfig.INSTANCE.maxCountIs1.get() && slot==1 )return 1;
            else return super.getSlotLimit(slot);
        }
    };
    // top 0 in potion
    // side 1 in food
    //bottom out 2 3 4
    public final IItemHandler itemHandlerProxy=new IItemHandler() {
        @Override
        public int getSlots() {
            return inventory.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot==0 && ApplyUtil.canApplyAsPotion(stack)){
                return inventory.insertItem(0, stack, simulate);
            }else if (slot==1 && ApplyUtil.canApplyStackAsFoods(stack)){
                return inventory.insertItem(1, stack, simulate);
            }
            else return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == 2) {
                return inventory.extractItem(2, amount, simulate);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return
                    (slot==0 && ApplyUtil.canApplyAsPotion(stack))
                            || (slot == 1 && ApplyUtil.canApplyStackAsFoods(stack));
        }
    };


    public final LazyOptional<IItemHandler> optional_proxy=LazyOptional.of(() ->itemHandlerProxy);


    public  TileEntityBlockCrafting( BlockPos pos, BlockState blockState) {
        super(AddPotionMain.TILE_ENTITY_BLOCK_AP_CRAFTING_TABLE.get(),pos,blockState);
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag=super.serializeNBT();
        tag.put(NBT_INVENTORY,inventory.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inventory.deserializeNBT(nbt.getCompound(NBT_INVENTORY));
        super.deserializeNBT(nbt);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap== ForgeCapabilities.ITEM_HANDLER){
            return optional_proxy.cast();
        }
        else return super.getCapability(cap,side);
    }
    public boolean isPowered(){
        return level.hasNeighborSignal(this.getBlockPos());
    }
    public boolean isSpare(){
        return inventory.getStackInSlot(2).isEmpty();
    }
    public static boolean canWork(TileEntityBlockCrafting te){
        return te.isPowered() && te.isSpare();
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState state, T t) {
        if (!level.isClientSide && (t instanceof TileEntityBlockCrafting)){
            TileEntityBlockCrafting tileEntityBlockCrafting=(TileEntityBlockCrafting) t;
            ItemStackHandler itemStackHandler=tileEntityBlockCrafting.inventory;
            if (canWork(tileEntityBlockCrafting)){

                ItemStack food = itemStackHandler.getStackInSlot(1);
                ItemStack potion = itemStackHandler.getStackInSlot(0);
                APCraftEvent.Pre pre_event=new APCraftEvent.Pre(null, food, potion);
                if (!MinecraftForge.EVENT_BUS.post(pre_event)){
                    //apply event
                    ItemStack result=PotionProcess.processAuto(pre_event.potionStack,pre_event.foodStack);
                    //put event
                    APCraftEvent.Post post_event=new APCraftEvent.Post(null, result);
                    MinecraftForge.EVENT_BUS.post(post_event);
                    //add result
                    itemStackHandler.setStackInSlot(3,post_event.result.copy());
                    //clean food and potion
                    itemStackHandler.setStackInSlot(0,ItemStack.EMPTY.copy());
                    itemStackHandler.setStackInSlot(1,ItemStack.EMPTY.copy());
                }
            }
        }
    }
}
