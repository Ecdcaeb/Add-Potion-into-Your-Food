package com.Hileb.add_potion.common.util.compat;

import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.List;

public class LoadMods {
	public static boolean botania;
	public static void init() {
		botania = ModList.get().isLoaded("botania");
	}

	public static boolean isBotaniaPotion(ItemStack itemStack) {
		return botania && BotaniaUtil.isBotaniaPotion(itemStack);
	}

	public static List<MobEffectInstance> getBotaniaEffects(ItemStack itemStack) {
		return botania ? BotaniaUtil.getBotaniaEffects(itemStack) : Lists.newArrayList();
	}
}
