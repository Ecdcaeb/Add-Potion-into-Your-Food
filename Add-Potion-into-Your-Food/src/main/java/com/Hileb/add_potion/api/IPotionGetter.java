package com.Hileb.add_potion.api;

import com.Hileb.add_potion.common.potion.APotion;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * @Project Add-Potion-into-Your-Food
 * @Author Hileb
 * @Date 2023/10/28 21:13
 **/

@FunctionalInterface
public interface IPotionGetter {
    /**
     * @param stack ItemStack
     * @return the list of APotions for custom extraction. e.g. botanic , potion core ,vanilla , add_potion
     */
    List<APotion> get(ItemStack stack);
}
