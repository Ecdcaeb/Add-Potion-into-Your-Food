package com.Hileb.add_potion.api;


import com.Hileb.add_potion.APConfig;
import com.Hileb.add_potion.common.potion.APotion;
import com.Hileb.add_potion.common.potion.PotionUtil;
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
    /**
     * @param food 食物
     * @param potion 药水
     * @return 产物的结果，如果成功{@link InteractionResult.SUCCESS} 内含产物——食物的复制品。
     * 如果失败{@link InteractionResult.FAIL}，内部为 {@link ItemStack.EMPTY}.
     */
    public static InteractionResultHolder<ItemStack> process(ItemStack food, ItemStack potion){
        if (PotionUtil.getAPotionCount(food)+PotionUtil.getAPotionCount(potion)<= APConfig.ap_addLimit_desc){
            List<APotion> potions= PotionUtil.getAllEffect(potion);
            ItemStack stackOut=food.copy();
            PotionUtil.addAPotionToStack(stackOut,potions);
            return InteractionResultHolder.success(stackOut);
        }
        else return InteractionResultHolder.fail(ItemStack.EMPTY);
    }
}
