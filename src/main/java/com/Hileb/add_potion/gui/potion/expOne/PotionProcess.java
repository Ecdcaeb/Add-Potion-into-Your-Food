package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.init.ModConfig;
import com.Hileb.add_potion.util.potion.APotion;
import com.Hileb.add_potion.util.potion.PotionUtil;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PotionProcess {
	public static String process(Slot potion, Slot food) {
		if (PotionUtil.getAllEffect(potion.getStack()).size()+PotionUtil.getAllEffect(food.getStack()).size()<=ModConfig.entityElectricShakingConf.ap_addLimit_desc) {
			List<APotion> potionPotion=PotionUtil.getAllEffect(potion.getStack());
			if (potionPotion==null || potionPotion.size()<=0) {
				return "ap.noPotionEffect";
			}
			ItemStack stack=food.getStack().copy();
			PotionUtil.addAPotionToStack(stack,potionPotion);
			food.putStack(stack);
			potion.putStack(ItemStack.EMPTY);
			return "ap.craftSuccess";
		}
		else return "ap.craftOtOfLimit";
	}
	public static ItemStack processAuto(ItemStack potion, ItemStack food) {
		if (PotionUtil.getAllEffect(potion).size()+PotionUtil.getAllEffect(food).size()<=ModConfig.entityElectricShakingConf.ap_addLimit_desc) {
			List<APotion> potionPotion=PotionUtil.getAllEffect(potion);
			if (potionPotion==null || potionPotion.size()<=0) {
				return ItemStack.EMPTY;
			}
			ItemStack stackOut=food.copy();
			PotionUtil.addAPotionToStack(stackOut,potionPotion);

			return stackOut;
		}
		else return ItemStack.EMPTY;
	}
}
