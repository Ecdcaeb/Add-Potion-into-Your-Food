package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.event.APCraftEvent;
import com.Hileb.add_potion.gui.ModAPGui;
import com.Hileb.add_potion.init.ModConfig;
import com.Hileb.add_potion.util.potion.ApplyUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

//https://fmltutor.ustc-zzzz.net/3.4.2-GUI%E7%95%8C%E9%9D%A2%E7%9A%84%E4%B8%AA%E6%80%A7%E5%8C%96%E4%B8%8E%E7%89%A9%E5%93%81%E6%A7%BD%E7%9A%84%E6%B7%BB%E5%8A%A0.html
public class ContainerDemo extends AbstractContainerMenu {

    private final Container container=new SimpleContainer(2);

    protected Slot potionSlot;
    protected Slot foodSlot;
    Player playerMP;

    @SubscribeEvent
    public void onMessageEvent(ProcessEvent event){
        if (playerMP.getUUID().toString().equals(event.message.uuid)){
            onMessage(event.message);
        }
    }

    public void onMessage(ProcessMessage message){
        if (!playerMP.level.isClientSide) {
            if (message.message.equals(ProcessMessage.PROCESS)) {
                ItemStack food = foodSlot.getItem().copy();
                ItemStack potion = potionSlot.getItem().copy();
                if (!MinecraftForge.EVENT_BUS.post(new APCraftEvent.Pre(playerMP, food, potion))) {
                    foodSlot.set(food);
                    potionSlot.set(potion);
                    String tip = PotionProcess.process(potionSlot, foodSlot);
                    playerMP.displayClientMessage(Component.translatable(tip), true);
                }
            }
        }
    }

    public ContainerDemo(int containerId, Inventory playerInv)
    {
        super(ModAPGui.GUI_SERVER_POTION.get(),1);
        playerMP=playerInv.player;
        MinecraftForge.EVENT_BUS.register(this);

        this.addSlot(this.potionSlot = new Slot(container, 0, 13, 5)
        {
            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return super.mayPlace(stack) && ApplyUtil.applyPotion(stack);
            }
        });

        //2nd
        this.addSlot(this.foodSlot= new Slot(container, 1, 47, 32)
        {

            @Override
            public int getMaxStackSize() {
                return  ModConfig.entityElectricShakingConf.ap_maxIs1_desc?1:64;
            }


            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return super.mayPlace(stack) && ApplyUtil.applyFood(stack);
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(playerMP.getInventory(), j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(playerMP.getInventory(), i, 8 + i * 18, 109));
        }


    }
    public  Slot getPotionSlot(){return potionSlot;}
    public  Slot getFoodSlot(){return foodSlot;}



    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }


    @Override
    public void removed(Player playerIn)
    {
        super.removed(playerIn);

        if (!playerIn.level.isClientSide)
        {
            ItemStack goldStack = this.potionSlot.getItem();
            if (goldStack != ItemStack.EMPTY)
            {
                playerIn.addItem(goldStack);
                this.potionSlot.set(ItemStack.EMPTY);
            }
            ItemStack emeraldStack = this.foodSlot.getItem();
            if (emeraldStack != ItemStack.EMPTY)
            {
                playerIn.addItem(emeraldStack);
                this.foodSlot.set(ItemStack.EMPTY);
            }
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
            isMerged =moveItemStackTo(newStack, 2, 38, true);
        }
        else if (index >= 2 && index < 29)
        {
            isMerged =moveItemStackTo(newStack, 1, 2, false)
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
}
