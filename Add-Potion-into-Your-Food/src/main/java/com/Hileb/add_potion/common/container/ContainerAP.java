package com.Hileb.add_potion.common.container;

import com.Hileb.add_potion.APConfig;
import com.Hileb.add_potion.common.APRegisterHandler;
import com.Hileb.add_potion.api.events.APCraftEvent;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.api.PotionProcess;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ContainerAP extends AbstractContainerMenu {

    public int containerId;
    public  Inventory playerInventory;
    public  ContainerLevelAccess containerLevelAccess;
    public  ItemStackHandler containerBuildInInventory=new ItemStackHandler(2);
    public ContainerAP(int id,Inventory inventoryIn) {
        this(id,inventoryIn,ContainerLevelAccess.NULL);
    }

    public void handleMessage(){
        accessProcess(playerInventory.player);
    }
    public void accessProcess(Player player){
        ItemStack foodStack = foodSlot.getItem().copy();
        ItemStack potionStack = potionSlot.getItem().copy();
        APCraftEvent.Pre pre_event=new APCraftEvent.Pre(player, foodStack, potionStack);
        if (!MinecraftForge.EVENT_BUS.post(pre_event)){
            InteractionResultHolder<ItemStack> result=PotionProcess.process(pre_event.foodStack,pre_event.potionStack);
            if (result.getResult()== InteractionResult.SUCCESS){
                APCraftEvent.Post post_event=new APCraftEvent.Post(player,result.getObject());
                foodSlot.set(post_event.result);
                potionSlot.getItem().shrink(1);
                player.sendSystemMessage(Component.translatable("ap.craftSuccess"));
                player.level().playSound(player, player.getX(),player.getY(),player.getZ(), SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5f,player.level().random.nextFloat() * 0.15F + 0.6F);
            }else {
                player.sendSystemMessage(Component.translatable("ap.noPotionEffect"));
            }
        }
    }

    public Slot potionSlot;
    public Slot foodSlot;

    public ContainerAP(int id, Inventory inventoryIn, ContainerLevelAccess access){
        super(APRegisterHandler.CONTAINER_AP.get(), id);
        containerId=id;
        playerInventory=inventoryIn;
        containerLevelAccess=access;

        this.addSlot(this.potionSlot = new SlotItemHandler(containerBuildInInventory, 0, 13, 5)
        {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack)
            {
                return super.mayPlace(stack)&& ApplyUtil.canApplyAsPotion(playerInventory.player, stack);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }
        });
        //2nd
        this.addSlot(this.foodSlot= new SlotItemHandler(containerBuildInInventory, 1, 47, 32)
        {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack)
            {
                return super.mayPlace(stack)&& ApplyUtil.canApplyStackAsFoods(playerInventory.player, stack);
            }

            @Override
            public int getMaxStackSize() {
                return  APConfig.ap_maxIs1_desc ?1:64;
            }
            @Override
            public boolean mayPickup(Player playerIn)
            {
                return true;
            }
        });





        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(inventoryIn, i, 8 + i * 18, 109));
        }


    }




    @Override
    public ItemStack quickMoveStack(Player playerIn, int index)
    {
        Slot slot = slots.get(index);

        if (slot == null || !slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getItem(), oldStack = newStack.copy();

        //---------------------
        boolean isMerged = false;

        if (index == 0 || index == 1)
        {
            isMerged = moveItemStackTo(newStack, 2, 38, true);
        }
        else if (index >= 2 && index < 29)
        {
            isMerged = moveItemStackTo(newStack, 1, 2, false)
                    || moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 29, 38, false);
        }
        else if (index >= 29 && index < 38)
        {
            isMerged =  moveItemStackTo(newStack, 1, 2, false)
                    || moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 2, 29, false);
        }

        if (!isMerged)
        {
            return ItemStack.EMPTY;
        }
        //---------------------
        if (newStack.getCount() == 0)
        {
            slot.set(ItemStack.EMPTY);
        }
        else
        {
            slot.setChanged();
        }

        slot.onTake(playerIn, newStack);

        return oldStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(containerLevelAccess,player,APRegisterHandler.BLOCK_AP_CRAFTING_TABLE.get());
    }

    //onCloseContainer
    @Override
    public void removed(@Nonnull Player player) {
        super.removed(player);
        //解构

        if (!player.level().isClientSide){
            ItemStack stack;
            for(int i=0;i<2;i++){
                stack=containerBuildInInventory.getStackInSlot(i);
                if (!stack.isEmpty()){
                    ItemEntity itementity = player.drop(stack, false);
                    if (itementity != null) {
                        itementity.setNoPickUpDelay();
                        itementity.setTarget(player.getUUID());
                    }
                    containerBuildInInventory.setStackInSlot(i,ItemStack.EMPTY);
                }
            }
        }
    }
}
