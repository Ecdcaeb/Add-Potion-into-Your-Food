package com.Hileb.add_potion.common.blocks.entity;

import com.Hileb.add_potion.common.init.ModBlockEntities;
import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PotionFactoryBlockEntity extends BlockEntity implements WorldlyContainer {
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FOOD = 1;
	public static final int SLOT_RESULT1 = 2;
	public static final int SLOT_RESULT2 = 3;
	public static final int SLOT_RESULT3 = 4;
	public static final int SLOT_COUNT = 5;

	protected NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);


	public PotionFactoryBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.POTION_FACTORY.get(), blockPos, blockState);
	}

	public static void serverTick(Level level, BlockPos blockPos, @SuppressWarnings("unused") BlockState blockState, PotionFactoryBlockEntity blockEntity) {
		if(!level.isClientSide) {
			if(level.hasNeighborSignal(blockPos)) {
				ItemStack potion = blockEntity.getItem(SLOT_INPUT);
				ItemStack food = blockEntity.getItem(SLOT_FOOD);
				ItemStack result = APUtils.applyEffectsToFood(potion, food);
				for(int i = SLOT_RESULT1; i <= SLOT_RESULT3; ++i) {
					ItemStack slot = blockEntity.getItem(i);
					if(slot.isEmpty()) {
						blockEntity.setItem(i, result);
						potion.shrink(1);
						food.shrink(1);
					} else if(ItemStack.isSameItemSameTags(slot, result)) {
						blockEntity.getItem(i).grow(1);
						potion.shrink(1);
						food.shrink(1);
					}
				}
			}
		}
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.items);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		ContainerHelper.saveAllItems(compound, this.items);
	}

	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override @NotNull
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP) {
				return this.handlers[0].cast();
			}
			if (facing == Direction.DOWN) {
				return this.handlers[1].cast();
			}
			return this.handlers[2].cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
			handler.invalidate();
		}
	}

	@Override
	public void reviveCaps() {
		super.reviveCaps();
		this.handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
	}

	@Override
	public int getContainerSize() {
		return SLOT_COUNT;
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack itemStack) {
		return switch (index) {
			case SLOT_INPUT -> APUtils.canPlaceToPotionSlot(itemStack);
			case SLOT_FOOD -> APUtils.canPlaceToFoodSlot(itemStack);
			default -> false;
		};
	}

	@Override
	public ItemStack getItem(int index) {
		return this.items.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.items, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.items, index);
	}

	@Override
	public void setItem(int index, ItemStack itemStack) {
		this.items.set(index, itemStack);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		}
		return this.worldPosition.closerToCenterThan(player.position(), 64.0D);
	}

	@Override
	public void clearContent() {

	}

	private static final int[] SLOTS_FOR_UP = new int[]{SLOT_INPUT};
	private static final int[] SLOTS_FOR_DOWN = new int[]{SLOT_RESULT1, SLOT_RESULT2, SLOT_RESULT3};
	private static final int[] SLOTS_FOR_SIDES = new int[]{SLOT_FOOD};

	@Override
	public int[] getSlotsForFace(Direction direction) {
		if (direction == Direction.DOWN) {
			return SLOTS_FOR_DOWN;
		}
		return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
		return this.canPlaceItem(index, itemStack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
		return true;
	}
}
