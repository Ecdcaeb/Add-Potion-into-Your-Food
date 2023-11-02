package com.Hileb.add_potion.common.gui.potion.expOne;

import com.Hileb.add_potion.common.init.ModConfig;
import com.Hileb.add_potion.common.util.potion.APotion;
import com.Hileb.add_potion.common.util.potion.PotionUtil;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PotionProcess {
	public static String process(Slot potion, Slot food) {
		if (PotionUtil.getAllEffect(potion.getItem()).size() + PotionUtil.getAllEffect(food.getItem()).size() <= ModConfig.entityElectricShakingConf.ap_addLimit_desc) {
			List<APotion> potionPotion = PotionUtil.getAllEffect(potion.getItem());
			if (potionPotion==null || potionPotion.size()<=0) {
				return "ap.noPotionEffect";
			}
			ItemStack stack = food.getItem().copy();
			PotionUtil.addAPotionToStack(stack,potionPotion);
			food.set(stack);
			potion.set(ItemStack.EMPTY);
			return "ap.craftSuccess";
		}
		return "ap.craftOtOfLimit";
	}
	public static ItemStack processAuto(ItemStack potion, ItemStack food) {
		if (PotionUtil.getAllEffect(potion).size() + PotionUtil.getAllEffect(food).size() <= ModConfig.entityElectricShakingConf.ap_addLimit_desc) {
			List<APotion> potionPotion = PotionUtil.getAllEffect(potion);
			if (potionPotion==null || potionPotion.size() <= 0) {
				return ItemStack.EMPTY;
			}
			ItemStack stackOut = food.copy();
			PotionUtil.addAPotionToStack(stackOut,potionPotion);

			return stackOut;
		}
		return ItemStack.EMPTY;
	}
}
