package com.Hileb.add_potion.common.util.compat;

import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.List;

public final class LoadMods {
	public static boolean botania;
	public static boolean emeraldcraft;

	public static void init() {
		botania = ModList.get().isLoaded("botania");
		emeraldcraft = ModList.get().isLoaded("emeraldcraft");
	}

	public static boolean isBotaniaPotion(ItemStack itemStack) {
		return botania && BotaniaUtil.isBotaniaPotion(itemStack);
	}

	@Nullable
	public static ItemStack getBotaniaPotionRemaining(ItemStack itemStack) {
		return botania ? BotaniaUtil.getBotaniaPotionRemaining(itemStack) : null;
	}

	public static List<MobEffectInstance> getBotaniaEffects(ItemStack itemStack) {
		return botania ? BotaniaUtil.getBotaniaEffects(itemStack) : Lists.newArrayList();
	}

	public static void addEmeraldCraftPotions(List<Potion> beneficial, List<Potion> harmful) {
		if(emeraldcraft) {
			EmeraldCraftUtil.addECPotionsTo(beneficial, harmful);
		}
	}
}
