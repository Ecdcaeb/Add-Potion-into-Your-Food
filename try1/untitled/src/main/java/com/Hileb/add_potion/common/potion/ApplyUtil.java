package com.Hileb.add_potion.common.potion;

import com.Hileb.add_potion.common.event.APApplyEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;

public class ApplyUtil {
    public static boolean canApplyStackAsFoods(ItemStack stack){
        if (stack.isEmpty())return false;
        APApplyEvent event=new APApplyEvent.Potion(stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.applyResult== APApplyEvent.EnumApply.APLLY)return true;
        if (event.applyResult== APApplyEvent.EnumApply.FALSE)return false;
        if (isFood(stack) || isValliPotion(stack)){
            return true;
        }
//        else if (LoadMods.botania && stack.getItem() instanceof ItemBrewBase){
//                return true;
//        } else if (LoadMods.rustic && stack.getItem() instanceof rustic.common.items.ItemElixir){
//                return true;
//        } else
        return false;
    }
    public static boolean isFood(ItemStack stack){
        return stack.getItem().isEdible();
    }
    public static boolean isValliPotion(ItemStack stack){
        return stack.getItem()== Items.POTION || stack.getItem()==Items.LINGERING_POTION || stack.getItem()==Items.SPLASH_POTION;
    }
    public static boolean canApplyAsPotion(ItemStack stack){
        if (stack.isEmpty())return false;
        APApplyEvent event=new APApplyEvent.Potion(stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.applyResult== APApplyEvent.EnumApply.APLLY)return true;
        if (event.applyResult== APApplyEvent.EnumApply.FALSE)return false;
        return (canApplyStackAsFoods(stack) && !isFood(stack));
    }
}
