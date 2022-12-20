package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.init.ModConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

//https://fmltutor.ustc-zzzz.net/3.4.2-GUI%E7%95%8C%E9%9D%A2%E7%9A%84%E4%B8%AA%E6%80%A7%E5%8C%96%E4%B8%8E%E7%89%A9%E5%93%81%E6%A7%BD%E7%9A%84%E6%B7%BB%E5%8A%A0.html
public class ContainerDemo extends Container {

    private ItemStackHandler items = new ItemStackHandler(2);

    protected Slot potionSlot;
    protected Slot foodSlot;

    @SubscribeEvent
    public void omessage(ProcessEvent event){
        onMessage(event.message);
    }
    public ProcessMessage onMessage(ProcessMessage message){
        if (message.message.equals(ProcessMessage.PROCESS)){PotionProcess.process(potionSlot,foodSlot);}
        return message;
    }



    public ContainerDemo(EntityPlayer player)
    {
        super();
        MinecraftForge.EVENT_BUS.register(this);

        this.addSlotToContainer(this.potionSlot = new SlotItemHandler(items, 0, 13, 5)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return super.isItemValid(stack)&&(stack.getItem()==Items.POTIONITEM);
            }
            @Override
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return true;
            }
        });

        //2nd
        this.addSlotToContainer(this.foodSlot= new SlotItemHandler(items, 1, 47, 32)
        {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return super.isItemValid(stack)&&(stack.getItem() instanceof ItemFood || stack.getItem()==Items.POTIONITEM);
            }

            @Override
            public int getSlotStackLimit() {
                return  ModConfig.entityElectricShakingConf.ap_maxIs1_desc?1:64;
            }

            @Override
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return true;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 109));
        }


    }
    public  Slot getPotionSlot(){return potionSlot;}
    public  Slot getFoodSlot(){return foodSlot;}


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (playerIn.isServerWorld())
        {
            ItemStack goldStack = this.potionSlot.getStack();
            if (goldStack != ItemStack.EMPTY)
            {
                playerIn.addItemStackToInventory(goldStack);
                this.potionSlot.putStack(ItemStack.EMPTY);
            }
            ItemStack emeraldStack = this.foodSlot.getStack();
            if (emeraldStack != ItemStack.EMPTY)
            {
                playerIn.addItemStackToInventory(emeraldStack);
                this.foodSlot.putStack(ItemStack.EMPTY);
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
        {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

        //---------------------
        boolean isMerged = false;

        if (index == 0 || index == 1)
        {
            isMerged = mergeItemStack(newStack, 2, 38, true);
        }
        else if (index >= 2 && index < 29)
        {
            isMerged = mergeItemStack(newStack, 1, 2, false)
                    || mergeItemStack(newStack, 0, 1, false)
                    || mergeItemStack(newStack, 29, 38, false);
        }
        else if (index >= 29 && index < 38)
        {
            isMerged =  mergeItemStack(newStack, 1, 2, false)
                    || mergeItemStack(newStack, 0, 1, false)
                    || mergeItemStack(newStack, 2, 29, false);
        }

        if (!isMerged)
        {
            return ItemStack.EMPTY;
        }

        //---------------------


        if (newStack.getCount() == 0)
        {
            slot.putStack(ItemStack.EMPTY);
        }
        else
        {
            slot.onSlotChanged();
        }

        slot.onTake(playerIn, newStack);

        return oldStack;
    }
}
