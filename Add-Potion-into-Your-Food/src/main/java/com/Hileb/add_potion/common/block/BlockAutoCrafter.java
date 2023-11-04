package com.Hileb.add_potion.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

/**
 * @Project Add-Potion-into-Your-Food
 * @Author Hileb
 * @Date 2023/10/22 10:32
 **/
public class BlockAutoCrafter extends BaseEntityBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("block.add_potion.block_ap_crafting_table");
    public BlockAutoCrafter() {
        super(
                BlockBehaviour.Properties.of().
                        mapColor(MapColor.STONE).
                        instrument(NoteBlockInstrument.BASEDRUM).
                        requiresCorrectToolForDrops().strength(1.5F, 6.0F));

    }
    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return TileEntity::tick;
    }

    public static class TileEntity extends BlockEntity {
        public TileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
            super(pType, pPos, pBlockState);
        }
        public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState state, T t) {

        }
    }
}
