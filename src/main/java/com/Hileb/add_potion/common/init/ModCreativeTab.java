package com.Hileb.add_potion.common.init;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.CreativeModeTabEvent;

import static com.Hileb.add_potion.AddPotion.MODID;

public class ModCreativeTab {
	@SuppressWarnings("NotNullFieldNotInitialized")
	public static CreativeModeTab ITEM_GROUP;

	public static void creativeTabEvent(CreativeModeTabEvent.Register event) {
		ITEM_GROUP = event.registerCreativeModeTab(new ResourceLocation(MODID, "item_group"), builder -> builder
				.icon(() -> PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON))
				.title(Component.translatable("itemGroup.add_potion"))
				.displayItems((flag, output) -> {
					output.accept(ModItems.POTION_TABLE.get());
					output.accept(ModItems.POTION_FACTORY.get());
				})
		);
	}
}
