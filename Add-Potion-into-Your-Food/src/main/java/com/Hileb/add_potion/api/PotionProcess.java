package com.Hileb.add_potion.api;


import com.Hileb.add_potion.APConfig;
import com.Hileb.add_potion.common.potion.APotion;
import com.Hileb.add_potion.common.potion.PotionUtil;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PotionProcess {
    /**
     * @param food 食物
     * @param potion 药水
     * @return the result of process产物的结果，if success 如果成功{@link InteractionResult#SUCCESS} 内含产物——食物的复制品。The copy of result.
     * 如果失败{@link InteractionResult#FAIL}，内部为 {@link ItemStack#EMPTY}.If Fail,The empty itemStack.
     */
    public static InteractionResultHolder<ItemStack> process(ItemStack food, ItemStack potion){
        if (food.isEmpty() || potion.isEmpty())return InteractionResultHolder.fail(ItemStack.EMPTY);
        else {
            List<APotion> potions= PotionUtil.getAllEffect(potion);
            List<APotion> foods= PotionUtil.getAllEffect(food);
            if (potions.size()+foods.size()<= APConfig.ap_addLimit_desc){
                ItemStack stackOut=food.copy();
                PotionUtil.addAPotionToStack(stackOut,potions);
                return InteractionResultHolder.success(stackOut);
            }else return InteractionResultHolder.fail(ItemStack.EMPTY);
        }
    }
}
