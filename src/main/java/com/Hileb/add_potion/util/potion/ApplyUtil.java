package com.Hileb.add_potion.util.potion;

import com.Hileb.add_potion.event.APApplyEvent;
import com.Hileb.add_potion.gui.potion.expOne.LoadMods;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import vazkii.botania.common.item.brew.ItemBrewBase;

public class ApplyUtil {
    public static boolean applyFood(ItemStack stack){
        APApplyEvent event=new APApplyEvent.Potion(stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.applyResult== APApplyEvent.EnumApply.APLLY)return true;
        if (event.applyResult== APApplyEvent.EnumApply.FALSE)return false;
        if (stack.getItem() instanceof ItemFood ||stack.getItem()== Items.POTIONITEM){
            return true;
        }
        else if (LoadMods.botania && stack.getItem() instanceof ItemBrewBase){
                return true;
        } else if (LoadMods.rustic && stack.getItem() instanceof rustic.common.items.ItemElixir){
                return true;
        } else return false;
    }
    public static boolean applyPotion(ItemStack stack){
        APApplyEvent event=new APApplyEvent.Potion(stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.applyResult== APApplyEvent.EnumApply.APLLY)return true;
        if (event.applyResult== APApplyEvent.EnumApply.FALSE)return false;
        return (applyFood(stack) && (!(stack.getItem() instanceof ItemFood))) ;
    }
}
