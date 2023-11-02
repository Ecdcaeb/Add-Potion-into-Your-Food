package com.Hileb.add_potion.common.util.compat;

import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.brew.IBrewItem;

import java.util.List;

//Make sure that botania is loaded before calling <clinit> method of this class!
public class BotaniaUtil {
	public static boolean isBotaniaPotion(ItemStack itemStack) {
		return itemStack.getItem() instanceof IBrewItem;
	}

	public static List<MobEffectInstance> getBotaniaEffects(ItemStack itemStack) {
		if(itemStack.getItem() instanceof IBrewItem brewItem) {
			return brewItem.getBrew(itemStack).getPotionEffects(itemStack);
		}
		return Lists.newArrayList();
	}
}
