package com.Hileb.add_potion.common.potion;

import com.Hileb.add_potion.api.AddPotionRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ApplyUtil {
    public static boolean canApplyStackAsFoods(@Nullable Player player, ItemStack stack){
        if (stack.isEmpty())return false;
        boolean isOk=false;
        AddPotionRegistries.Result result;
        for( AddPotionRegistries.Adjuster predicate:  AddPotionRegistries.FOODS){
            result=predicate.test(player,stack);
            if (result==  AddPotionRegistries.Result.APPLY)isOk=true;
            else if (result==  AddPotionRegistries.Result.REJECT)return false;
        }
        return isOk;
    }
    public static boolean canApplyAsPotion(@Nullable Player player,ItemStack stack){
        if (stack.isEmpty())return false;
        boolean isOk=false;
        AddPotionRegistries.Result result;
        for( AddPotionRegistries.Adjuster predicate:  AddPotionRegistries.POTION){
            result=predicate.test(player,stack);
            if (result==  AddPotionRegistries.Result.APPLY)isOk=true;
            else if (result==  AddPotionRegistries.Result.REJECT)return false;
        }
        return isOk;
    }
}
