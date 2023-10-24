package com.Hileb.add_potion.common.container;

import com.Hileb.add_potion.AddPotionMain;
import com.Hileb.add_potion.common.event.APCraftEvent;
import com.Hileb.add_potion.common.init.ModConfig;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.common.potion.PotionProcess;
import com.Hileb.add_potion.network.ButtonDownEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ContainerAP extends AbstractContainerMenu {
   public  int coolDown=0;
    public int containerId;
    public  Inventory playerInventory;
    public  ContainerLevelAccess containerLevelAccess;
    public ItemStackHandler tileEntityInventory;
    public ItemStackHandler guiContainerInventory=new ItemStackHandler(3);
    public ContainerAP(int id,Inventory inventoryIn) {
        this(id,inventoryIn,ContainerLevelAccess.NULL,new ItemStackHandler(5));
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onMessageEvent(ButtonDownEvent event){
        AddPotionMain.LOGGER.warn("2p");
        if (!event.getEntity().getLevel().isClientSide){
            if (canProcess()){
                Player player=event.getEntity();
                if (player.getUUID().toString().equals(playerInventory.player.getUUID().toString())){
                    AddPotionMain.LOGGER.warn("1p");
                    accessProcess(playerInventory.player);
                    resetProcessState();
                }
            }
        }
    }
    @SubscribeEvent
    public void onUpdate(TickEvent.LevelTickEvent event){
        coolDown++;
    }
    public boolean canProcess(){
        return coolDown>=10;
    }
    public void resetProcessState(){
        coolDown=0;
    }

    public void accessProcess(Player player){
        ItemStack foodStack = foodSlot.getItem();
        ItemStack potionStack = potionSlot.getItem();
        APCraftEvent.Pre pre_event=new APCraftEvent.Pre(player, foodStack, potionStack);
        if (!MinecraftForge.EVENT_BUS.post(pre_event)){


            ItemStack result=PotionProcess.processAuto(pre_event.potionStack,pre_event.foodStack);
            APCraftEvent.Post post_event=new APCraftEvent.Post(player, result);


            MinecraftForge.EVENT_BUS.post(post_event);



            foodSlot.set(post_event.result);
            potionSlot.set(ItemStack.EMPTY);

        }
        AddPotionMain.LOGGER.warn("post "+ potionSlot.getItem());//debug
        AddPotionMain.LOGGER.warn("food "+ foodSlot.getItem());//debug
    }

    public Slot potionSlot;
    public Slot foodSlot;

    public ContainerAP(int id, Inventory inventoryIn, ContainerLevelAccess access, ItemStackHandler itemStackHandler){
        super(AddPotionMain.CONTAINER_AP.get(), id);
        containerId=id;
        playerInventory=inventoryIn;
        containerLevelAccess=access;
        tileEntityInventory=itemStackHandler;


        //normal hand slots
        //1st
        this.addSlot(this.potionSlot = new SlotItemHandler(guiContainerInventory, 0, 13, 5)
        {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack)
            {
                return super.mayPlace(stack)&& ApplyUtil.canApplyAsPotion(stack);
            }


            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }
        });
        //2nd
        this.addSlot(this.foodSlot= new SlotItemHandler(guiContainerInventory, 1, 47, 32)
        {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack)
            {
                return super.mayPlace(stack)&& ApplyUtil.canApplyStackAsFoods(stack);
            }

            @Override
            public int getMaxStackSize() {
                return  ModConfig.INSTANCE.maxCountIs1.get()?1:64;
            }
            @Override
            public boolean mayPickup(Player playerIn)
            {
                return true;
            }
        });
        this.addSlot(this.foodSlot= new SlotItemHandler(guiContainerInventory, 2, 13, 32)
        {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack)
            {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn)
            {
                return true;
            }
        });




        //auto craft
        //3rd
        this.addSlot(new SlotItemHandler(tileEntityInventory, 0, 80, 5)
        {
            @Override
            public boolean mayPickup(Player playerIn)
            {
                return true;
            }
        });
        //2nd
        this.addSlot(new SlotItemHandler(tileEntityInventory, 1, 100, 5)
        {
            @Override
            public boolean mayPickup(Player playerIn)
            {
                return true;
            }
        });
        //2nd
        this.addSlot(new SlotItemHandler(tileEntityInventory, 2, 90,32)
        {
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

        if (index <6)
        {
            isMerged =moveItemStackTo(newStack, 5, 41, true);
        }
        else if (index < 32)
        {
            isMerged =moveItemStackTo(newStack, 1, 2, false)
                    || moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 2, 5, false)
                    || moveItemStackTo(newStack, 32, 41, false);
        }
        else if (index < 41)
        {
            isMerged =  moveItemStackTo(newStack, 1, 2, false)
                    || moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 2, 5, false)
                    || moveItemStackTo(newStack, 2, 32, false);
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
        return AbstractContainerMenu.stillValid(containerLevelAccess,player,AddPotionMain.BLOCK_AP_CRAFTING_TABLE.get());
    }

    //onCloseContainer
    @Override
    public void removed(@Nonnull Player player) {
        super.removed(player);
        //解构
        MinecraftForge.EVENT_BUS.unregister(this);
        if (!player.level.isClientSide){
            for(int i=0;i<guiContainerInventory.getSlots();i++){
                ItemStack stack=guiContainerInventory.getStackInSlot(i);
                boolean shouldDropItem=!player.getInventory().add(stack.copy());
                if (shouldDropItem){
                    player.drop(stack.copy(),false);
                }
                guiContainerInventory.setStackInSlot(i,ItemStack.EMPTY);
            }
        }
    }
}
