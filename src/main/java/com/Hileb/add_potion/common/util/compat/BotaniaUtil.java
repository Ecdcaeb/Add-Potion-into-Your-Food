package com.Hileb.add_potion.common.util.compat;

import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.brew.IBrewItem;
import vazkii.botania.common.item.brew.ItemBrewBase;

import javax.annotation.Nullable;
import java.util.List;

//Make sure that botania is loaded before calling <clinit> method of this class!
public final class BotaniaUtil {
	public static boolean isBotaniaPotion(ItemStack itemStack) {
		return itemStack.getItem() instanceof IBrewItem;
	}

	@Nullable
	public static ItemStack getBotaniaPotionRemaining(ItemStack itemStack) {
		if(itemStack.getItem() instanceof ItemBrewBase itemBrewBase) {
			int swigs = itemBrewBase.getSwigsLeft(itemStack);
			if (swigs == 1) {
				return itemBrewBase.getBaseStack();
			}

			ItemStack ret = itemStack.copy();
			itemBrewBase.setSwigsLeft(ret, swigs - 1);
			return ret;
		}
		return null;
	}

	public static List<MobEffectInstance> getBotaniaEffects(ItemStack itemStack) {
		if(itemStack.getItem() instanceof IBrewItem brewItem) {
			return brewItem.getBrew(itemStack).getPotionEffects(itemStack);
		}
		return Lists.newArrayList();
	}
}
