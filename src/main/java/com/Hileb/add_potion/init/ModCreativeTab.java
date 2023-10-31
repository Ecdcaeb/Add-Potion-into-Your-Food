package com.Hileb.add_potion.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import static com.Hileb.add_potion.IdlFramework.MODID;

public class ModCreativeTab {
	public static final CreativeModeTab IDL_MISC = new CreativeModeTab(MODID) {
		@Override
		public ItemStack makeIcon() {
			return PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON);
		}
	};
}
