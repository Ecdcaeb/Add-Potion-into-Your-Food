package com.Hileb.add_potion.util.potion;

import com.Hileb.add_potion.event.APApplyEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;

public class ApplyUtil {
    public static boolean applyFood(ItemStack stack){
        if (stack.isEmpty())return false;
        APApplyEvent event=new APApplyEvent.Potion(stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.applyResult== APApplyEvent.EnumApply.APLLY)return true;
        if (event.applyResult== APApplyEvent.EnumApply.FALSE)return false;

        if (stack.getItem().isEdible())return true;
        else if (applyPotion(stack))return true;
        else if (stack.getItem() instanceof ArmorItem)return true;
        //TODO: other mods
        else return false;
    }
    public static boolean applyPotion(ItemStack stack){
        if (stack.isEmpty())return false;
        APApplyEvent event=new APApplyEvent.Potion(stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.applyResult== APApplyEvent.EnumApply.APLLY)return true;
        if (event.applyResult== APApplyEvent.EnumApply.FALSE)return false;
        if (stack.getItem()== Items.POTION || stack.getItem()==Items.LINGERING_POTION || stack.getItem()==Items.SPLASH_POTION){
            return true;
        }
        //TODO: other mods
        else return false;
    }
}
