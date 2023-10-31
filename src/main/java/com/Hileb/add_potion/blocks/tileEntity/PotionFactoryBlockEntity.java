package com.Hileb.add_potion.blocks.tileEntity;

import com.Hileb.add_potion.gui.potion.expOne.PotionProcess;
import com.Hileb.add_potion.init.ModBlockEntities;
import com.Hileb.add_potion.util.potion.ApplyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PotionFactoryBlockEntity extends BaseContainerBlockEntity {
	public ItemStackHandler slotPotion = new ItemStackHandler();
	public ItemStackHandler slotFood = new ItemStackHandler();
	public ItemStackHandler slotOut = new ItemStackHandler(3);


	public PotionFactoryBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.AUTO_AP_CRAFTING_TILE_ENTITY.get(), blockPos, blockState);
	}


	@Override
	public void update() {

		if (!world.isRemote) {
			if(world.isBlockPowered(pos)) {
				if (slotOut.getStackInSlot(0).isEmpty()) {
					if (!slotPotion.getStackInSlot(0).isEmpty()) {
						if (!slotFood.getStackInSlot(0).isEmpty()) {
							ItemStack stack=PotionProcess.processAuto(slotPotion.getStackInSlot(0),slotFood.getStackInSlot(0));
							if (!stack.isEmpty()) {
								slotFood.setStackInSlot(0,ItemStack.EMPTY);
								slotPotion.getStackInSlot(0).shrink(1);
								slotOut.setStackInSlot(0,stack.copy());
							}
						}
					}
				}
			}


			ItemStack potion=slotPotion.getStackInSlot(0);
			if (!ApplyUtil.applyPotion(potion)) {
				if (slotOut.getStackInSlot(1).isEmpty()) {
					slotOut.setStackInSlot(1,potion.copy());
					slotPotion.setStackInSlot(0,ItemStack.EMPTY);
				}
			}
			ItemStack food=slotFood.getStackInSlot(0);
			if (!ApplyUtil.applyPotion(food)) {
				if (slotOut.getStackInSlot(2).isEmpty()) {
					slotOut.setStackInSlot(2,food.copy());
					slotFood.setStackInSlot(0,ItemStack.EMPTY);
				}
			}
		}
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.slotPotion.deserializeNBT(compound.getCompound("SlotPotion"));
		this.slotFood.deserializeNBT(compound.getCompound("SlotFood"));
		this.slotOut.deserializeNBT(compound.getCompound("SlotOut"));
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.put("SlotPotion", this.slotPotion.serializeNBT());
		compound.put("SlotFood", this.slotFood.serializeNBT());
		compound.put("SlotOut", this.slotOut.serializeNBT());
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container.add_potion.potion_factory");
	}

	@Override
	protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
		return null;
	}

	@Override @NotNull
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
			ItemStackHandler result;
			if (facing == Direction.UP) {
				result = this.slotPotion;
			} else if (facing == Direction.DOWN) {
				result = this.slotOut;
			} else {
				result = this.slotFood;
			}
			return LazyOptional.of(() -> result).cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int getContainerSize() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getItem(int p_18941_) {
		return null;
	}

	@Override
	public ItemStack removeItem(int p_18942_, int p_18943_) {
		return null;
	}

	@Override
	public ItemStack removeItemNoUpdate(int p_18951_) {
		return null;
	}

	@Override
	public void setItem(int p_18944_, ItemStack p_18945_) {

	}

	@Override
	public boolean stillValid(Player p_18946_) {
		return false;
	}

	@Override
	public void clearContent() {

	}
}
