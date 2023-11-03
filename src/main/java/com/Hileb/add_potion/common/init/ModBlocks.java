package com.Hileb.add_potion.common.init;

import com.Hileb.add_potion.common.blocks.PotionFactoryBlock;
import com.Hileb.add_potion.common.gui.PotionTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Hileb.add_potion.AddPotion.MODID;

public class ModBlocks {
	private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	@SuppressWarnings("deprecation")
	public static final RegistryObject<Block> POTION_TABLE = REGISTER.register(
			"potion_table", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F)) {
				private static final Component CONTAINER_TITLE = new TranslatableComponent("container.potion_table");

				@Override
				public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player p_57086_, InteractionHand p_57087_, BlockHitResult p_57088_) {
					if (level.isClientSide) {
						return InteractionResult.SUCCESS;
					} else {
						p_57086_.openMenu(blockState.getMenuProvider(level, blockPos));
						p_57086_.awardStat(Stats.INTERACT_WITH_STONECUTTER);
						return InteractionResult.CONSUME;
					}
				}

				@Override
				public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
					return new SimpleMenuProvider((id, inventory, player) -> new PotionTableMenu(id, inventory, ContainerLevelAccess.create(level, blockPos)), CONTAINER_TITLE);
				}
			}
	);

	public static final RegistryObject<Block> POTION_FACTORY = REGISTER.register(
			"potion_factory", () -> new PotionFactoryBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F))
	);

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
