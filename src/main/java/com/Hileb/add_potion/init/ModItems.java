package com.Hileb.add_potion.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Hileb.add_potion.IdlFramework.MODID;

@SuppressWarnings("unused")
public class ModItems {
	private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Item> POTION_TABLE = REGISTER.register("potion_table", () -> new BlockItem(ModBlocks.POTION_TABLE.get(), new Item.Properties().tab(ModCreativeTab.IDL_MISC)));

	public static final RegistryObject<Item> POTION_FACTORY = REGISTER.register("potion_factory", () -> new BlockItem(ModBlocks.POTION_FACTORY.get(), new Item.Properties().tab(ModCreativeTab.IDL_MISC)));

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
