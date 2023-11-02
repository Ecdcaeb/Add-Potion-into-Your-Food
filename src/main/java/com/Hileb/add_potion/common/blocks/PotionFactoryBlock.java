package com.Hileb.add_potion.common.blocks;

import com.Hileb.add_potion.common.blocks.entity.PotionFactoryBlockEntity;
import com.Hileb.add_potion.common.init.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PotionFactoryBlock extends BaseEntityBlock {
	public PotionFactoryBlock(Properties props) {
		super(props);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
		if(level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		BlockEntity blockEntity = level.getBlockEntity(blockPos);
		if(blockEntity instanceof PotionFactoryBlockEntity potionFactoryBlockEntity) {
			player.sendMessage(new TextComponent("Potion Slot: ").withStyle(ChatFormatting.RED), Util.NIL_UUID);
			player.sendMessage(getItemStackInformation(potionFactoryBlockEntity.getItem(PotionFactoryBlockEntity.SLOT_INPUT)), Util.NIL_UUID);

			player.sendMessage(new TextComponent("Food Slot: ").withStyle(ChatFormatting.RED), Util.NIL_UUID);
			player.sendMessage(getItemStackInformation(potionFactoryBlockEntity.getItem(PotionFactoryBlockEntity.SLOT_FOOD)), Util.NIL_UUID);

			player.sendMessage(new TextComponent("Result Slot: ").withStyle(ChatFormatting.RED), Util.NIL_UUID);
			for(int i = PotionFactoryBlockEntity.SLOT_RESULT1; i <= PotionFactoryBlockEntity.SLOT_RESULT3; ++i) {
				player.sendMessage(getItemStackInformation(potionFactoryBlockEntity.getItem(i)), Util.NIL_UUID);
			}
		}
		return InteractionResult.CONSUME;
	}

	private static Component getItemStackInformation(ItemStack itemStack) {
		if(itemStack.isEmpty()) {
			return new TextComponent(" - Empty.").withStyle(ChatFormatting.GRAY);
		}
		return new TextComponent(String.format(" - [%s](%d).", itemStack.getItem().getRegistryName(), itemStack.getCount())).withStyle(ChatFormatting.GRAY);
	}

	@Override
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean b) {
		if(!newBlockState.is(blockState.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(blockPos);
			if(blockEntity instanceof PotionFactoryBlockEntity potionFactoryBlockEntity) {
				if (!level.isClientSide) {
					Containers.dropContents(level, blockPos, potionFactoryBlockEntity);
				}
				level.updateNeighbourForOutputSignal(blockPos, this);
			}
		}
		super.onRemove(blockState, level, blockPos, newBlockState, b);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState blockState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
	}

	@Override
	public PotionFactoryBlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PotionFactoryBlockEntity(blockPos, blockState);
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override @Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.POTION_FACTORY.get(), PotionFactoryBlockEntity::serverTick);
	}
}
