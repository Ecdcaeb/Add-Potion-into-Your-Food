package com.Hileb.add_potion.common.block;

import com.Hileb.add_potion.AddPotionMain;
import com.Hileb.add_potion.common.container.ContainerAP;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockAPCrafting extends BaseEntityBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("block.add_potion.block_ap_crafting_table");
    public BlockAPCrafting() {
        super(BlockBehaviour.Properties.of(Material.WOOD));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos,  Player entityPlayer, InteractionHand  hand, BlockHitResult  result) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity be=world.getBlockEntity(pos);
            if (be instanceof TileEntityBlockCrafting){
                TileEntityBlockCrafting te=(TileEntityBlockCrafting) be;
                NetworkHooks.openScreen((ServerPlayer) entityPlayer,new SimpleMenuProvider(
                        (id,inventory,player)->new ContainerAP(id,inventory,ContainerLevelAccess.create(world,pos),te.inventory),
                        CONTAINER_TITLE
                ));
                return InteractionResult.CONSUME;
            }else return InteractionResult.PASS;
        }

    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityBlockCrafting(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> type) {
        return type == AddPotionMain.TILE_ENTITY_BLOCK_AP_CRAFTING_TABLE.get() ? TileEntityBlockCrafting::tick : null;
    }
}
