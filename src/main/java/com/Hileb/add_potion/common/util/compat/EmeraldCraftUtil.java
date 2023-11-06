package com.Hileb.add_potion.common.util.compat;

import com.Hileb.add_potion.common.util.ModLogger;
import com.hexagram2021.emeraldcraft.common.register.ECPotions;
import net.minecraft.world.item.alchemy.Potion;

import java.util.List;

//Make sure that emeraldcraft is loaded before calling <clinit> method of this class!
public final class EmeraldCraftUtil {
	public static void addECPotionsTo(List<Potion> beneficial, List<Potion> harmful) {
		ModLogger.Debug("Adding Emerald Craft potions to apothecary villagers' trading list.");
		beneficial.add(ECPotions.ABSORPTION);
		beneficial.add(ECPotions.SATURATION);
		harmful.add(ECPotions.BLINDNESS);
		harmful.add(ECPotions.GLOWING);
		harmful.add(ECPotions.HUNGER);
		harmful.add(ECPotions.WITHER);
	}
}
