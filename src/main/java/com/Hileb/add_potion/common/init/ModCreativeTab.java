package com.Hileb.add_potion.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.Hileb.add_potion.AddPotion.MODID;

public class ModCreativeTab {
	private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	public static RegistryObject<CreativeModeTab> ITEM_GROUP = register(
			"add_potion",
			Component.translatable("itemGroup.add_potion"),
			() -> PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON),
			(flag, output) -> {
				output.accept(ModItems.POTION_TABLE.get());
				output.accept(ModItems.POTION_FACTORY.get());
			}
	);

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}

	@SuppressWarnings("SameParameterValue")
	private static RegistryObject<CreativeModeTab> register(String name, Component title, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator generator) {
		return REGISTER.register(name, () -> CreativeModeTab.builder().title(title).icon(icon).displayItems(generator).build());
	}
}
