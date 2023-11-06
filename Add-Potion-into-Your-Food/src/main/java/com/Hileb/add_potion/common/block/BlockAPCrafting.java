package com.Hileb.add_potion.common.block;

import com.Hileb.add_potion.common.container.ContainerAP;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.extensions.IForgeServerPlayer;

import java.util.function.Consumer;

public class BlockAPCrafting extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("block.add_potion.block_ap_crafting_table");

    public BlockAPCrafting() {
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

    public InteractionResult use(BlockState state, Level world, BlockPos pos,  Player entityPlayer, InteractionHand  hand, BlockHitResult  result) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            world.playSound(entityPlayer, pos.getX(),pos.getY(),pos.getZ(), SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5f,world.random.nextFloat() * 0.15F + 0.6F);
            /*
              {@link IForgeServerPlayer#openMenu(MenuProvider, BlockPos)}
              {@link IForgeServerPlayer#openMenu(MenuProvider, Consumer)}
             * */


            ((IForgeServerPlayer)entityPlayer).openMenu(
                    new SimpleMenuProvider(
                    (id,inventory,player)->new ContainerAP(id,inventory,ContainerLevelAccess.create(world,pos)),
                    CONTAINER_TITLE),pos);
            return InteractionResult.CONSUME;
        }
    }
}
