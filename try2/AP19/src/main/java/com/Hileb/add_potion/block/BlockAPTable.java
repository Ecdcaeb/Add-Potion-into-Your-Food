package com.Hileb.add_potion.block;


import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockAPTable extends Block {

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(p_52233_.getMenuProvider(p_52234_, p_52235_));
            return InteractionResult.CONSUME;
        }
    }


}
