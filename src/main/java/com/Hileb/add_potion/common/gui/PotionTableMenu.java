package com.Hileb.add_potion.common.gui;

import com.Hileb.add_potion.api.event.APCraftEvent;
import com.Hileb.add_potion.common.init.ModBlocks;
import com.Hileb.add_potion.common.init.ModMenuTypes;
import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class PotionTableMenu extends AbstractContainerMenu {
	private static final int SLOT_POTION = 0;
	private static final int SLOT_FOOD = 1;
	private static final int INV_SLOT_START = 2;
	private static final int INV_SLOT_END = 29;
	private static final int USE_ROW_SLOT_START = 29;
	private static final int USE_ROW_SLOT_END = 38;

	private final ContainerLevelAccess access;

	final Slot potionSlot;
	final Slot foodSlot;

	public final Container container = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			PotionTableMenu.this.slotsChanged(this);
		}
	};

	public PotionTableMenu(int id, Inventory inventory) {
		this(id, inventory, ContainerLevelAccess.NULL);
	}

	public PotionTableMenu(int id, Inventory inventory, ContainerLevelAccess access) {
		super(ModMenuTypes.POTION_TABLE_MENU.get(), id);
		this.access = access;
		this.potionSlot = this.addSlot(new Slot(this.container, SLOT_POTION, 43, 24) {
			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public boolean mayPlace(ItemStack itemStack) {
				return APUtils.canPlaceToPotionSlot(itemStack);
			}
		});
		this.foodSlot = this.addSlot(new Slot(this.container, SLOT_FOOD, 77, 51) {
			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public boolean mayPlace(ItemStack itemStack) {
				return APUtils.canPlaceToFoodSlot(itemStack);
			}
		});

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean clickMenuButton(Player player, int index) {
		if(index == 0) {
			if(this.potionSlot.hasItem() && this.foodSlot.hasItem()) {
				ItemStack result = APUtils.applyEffectsToFood(player, this.potionSlot.getItem(), this.foodSlot.getItem());
				APCraftEvent event = new APCraftEvent(player, this.potionSlot.getItem(), this.foodSlot.getItem(), result);
				MinecraftForge.EVENT_BUS.post(event);
				this.potionSlot.getItem().shrink(1);
				this.foodSlot.set(event.getOutput());
			}
		}
		return true;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(this.access, player, ModBlocks.POTION_TABLE.get());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack ret = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack take = slot.getItem();
			ret = take.copy();
			if(index == SLOT_POTION) {
				if(!this.moveItemStackTo(take, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
					return ItemStack.EMPTY;
				}
			} else if(index == SLOT_FOOD) {
				if(!this.moveItemStackTo(take, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
					return ItemStack.EMPTY;
				}
			} else if(index >= INV_SLOT_START && index < INV_SLOT_END) {
				if(this.potionSlot.mayPlace(take)) {
					if(!this.moveItemStackTo(take, SLOT_POTION, SLOT_POTION + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if(this.foodSlot.mayPlace(take)) {
					if(!this.moveItemStackTo(take, SLOT_FOOD, SLOT_FOOD + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else {
					if(!this.moveItemStackTo(take, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else {
				if(this.potionSlot.mayPlace(take)) {
					if(!this.moveItemStackTo(take, SLOT_POTION, SLOT_POTION + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if(this.foodSlot.mayPlace(take)) {
					if(!this.moveItemStackTo(take, SLOT_FOOD, SLOT_FOOD + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else {
					if(!this.moveItemStackTo(take, INV_SLOT_START, INV_SLOT_END, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if(take.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			}

			slot.setChanged();
			if (take.getCount() == ret.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, take);
			this.broadcastChanges();
		}

		return ret;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.access.execute((level, blockPos) -> this.clearContainer(player, this.container));
	}
}
