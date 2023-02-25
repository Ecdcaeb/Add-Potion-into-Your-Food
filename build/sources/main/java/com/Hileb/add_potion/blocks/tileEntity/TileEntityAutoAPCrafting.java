package com.Hileb.add_potion.blocks.tileEntity;

import com.Hileb.add_potion.gui.potion.expOne.PotionProcess;
import com.Hileb.add_potion.util.potion.ApplyUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityAutoAPCrafting extends TileEntity implements ITickable{
    public ItemStackHandler slotPotion=new ItemStackHandler();
    public ItemStackHandler slotFood=new ItemStackHandler();
    public ItemStackHandler slotOut=new ItemStackHandler(3);


    public TileEntityAutoAPCrafting(){

    }


    @Override
    public void update() {

        if (!world.isRemote){
            if(world.isBlockPowered(pos)){
                if (slotOut.getStackInSlot(0).isEmpty()){
                    if (!slotPotion.getStackInSlot(0).isEmpty()){
                        if (!slotFood.getStackInSlot(0).isEmpty()){
                            ItemStack stack=PotionProcess.processAuto(slotPotion.getStackInSlot(0),slotFood.getStackInSlot(0));
                            if (!stack.isEmpty()){
                                slotFood.setStackInSlot(0,ItemStack.EMPTY);
                                slotPotion.getStackInSlot(0).shrink(1);
                                slotOut.setStackInSlot(0,stack.copy());
                            }
                        }
                    }
                }
            }


            ItemStack potion=slotPotion.getStackInSlot(0);
            if (!ApplyUtil.applyPotion(potion)){
                if (slotOut.getStackInSlot(1).isEmpty()){
                    slotOut.setStackInSlot(1,potion.copy());
                    slotPotion.setStackInSlot(0,ItemStack.EMPTY);
                }
            }
            ItemStack food=slotFood.getStackInSlot(0);
            if (!ApplyUtil.applyPotion(food)){
                if (slotOut.getStackInSlot(2).isEmpty()){
                    slotOut.setStackInSlot(2,food.copy());
                    slotFood.setStackInSlot(0,ItemStack.EMPTY);
                }
            }




        }
    }


    //IO : write and read from NBT
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        slotPotion.deserializeNBT(compound.getCompoundTag("SlotPotion"));
        slotFood.deserializeNBT(compound.getCompoundTag("SlotFood"));
        slotOut.deserializeNBT(compound.getCompoundTag("SlotOut"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("SlotPotion",slotPotion.serializeNBT());
        compound.setTag("SlotFood",slotFood.serializeNBT());
        compound.setTag("SlotOut",slotOut.serializeNBT());
        return super.writeToNBT(compound);
    }
    //cap:
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            T result;
            if (facing==EnumFacing.UP){
                result=(T)slotPotion;
            }else if (facing==EnumFacing.DOWN){
                result=(T)slotOut;
            }else {
                result=(T)slotFood;
            }
            return result;
        }
        return super.getCapability(capability, facing);
    }

}
