package com.Hileb.add_potion.common.potion;


import com.Hileb.add_potion.APConfig;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.List;

public class PotionProcess {
    @Deprecated
    public static ItemStack processAuto(ItemStack potion, ItemStack food){
        List<APotion> potions=PotionUtil.getAllEffect(potion);
        List<APotion> foods=PotionUtil.getAllEffect(food);
        if (potions.size()+foods.size()<= APConfig.ap_addLimit_desc){
            ItemStack stackOut=food.copy();
            PotionUtil.addAPotionToStack(stackOut,potions);
            return stackOut;
        }
        else return ItemStack.EMPTY;
    }

    /**
     * @param food 食物
     * @param potion 药水
     * @return 产物的结果，如果成功{@link InteractionResult.SUCCESS} 内含产物——食物的复制品。
     * 如果失败{@link InteractionResult.FAIL}，内部为 {@link ItemStack.EMPTY}.
     */
    public static InteractionResultHolder<ItemStack> process(ItemStack food, ItemStack potion){
        List<APotion> potions=PotionUtil.getAllEffect(potion);
        List<APotion> foods=PotionUtil.getAllEffect(food);
        if (potions.size()+foods.size()<= APConfig.ap_addLimit_desc){
            ItemStack stackOut=food.copy();
            PotionUtil.addAPotionToStack(stackOut,potions);
            return InteractionResultHolder.success(stackOut);
        }
        else return InteractionResultHolder.fail(ItemStack.EMPTY);
    }
}
