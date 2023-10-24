package com.Hileb.add_potion.common.potion;


import com.Hileb.add_potion.common.init.ModConfig;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PotionProcess {

    public static ItemStack processAuto(ItemStack potion, ItemStack food){
        if (PotionUtil.getAllEffect(potion).size()+PotionUtil.getAllEffect(food).size()<= ModConfig.INSTANCE.addLimit.get()){
            List<APotion> potionPotion=PotionUtil.getAllEffect(potion);
            if (potionPotion==null || potionPotion.size() == 0){
                return ItemStack.EMPTY;
            }
            ItemStack stackOut=food.copy();
            PotionUtil.addAPotionToStack(stackOut,potionPotion);

            return stackOut;
        }
        else return ItemStack.EMPTY;
    }
}
