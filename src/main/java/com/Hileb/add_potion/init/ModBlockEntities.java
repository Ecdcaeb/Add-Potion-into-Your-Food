package com.Hileb.add_potion.init;

import com.Hileb.add_potion.blocks.tileEntity.PotionFactoryBlockEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Hileb.add_potion.IdlFramework.MODID;

@SuppressWarnings("ConstantConditions")
public class ModBlockEntities {
	private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

	public static final RegistryObject<BlockEntityType<PotionFactoryBlockEntity>> AUTO_AP_CRAFTING_TILE_ENTITY = REGISTER.register(
			"block_auto_ap_crafting", () -> new BlockEntityType<>(
					PotionFactoryBlockEntity::new, ImmutableSet.of(ModBlocks.POTION_FACTORY.get()), null
			)
	)

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
