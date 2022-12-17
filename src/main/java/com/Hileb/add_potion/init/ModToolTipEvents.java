package com.Hileb.add_potion.init;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.util.Reference;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
//public class ModToolTipEvents {
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public static void onTooltip(ItemTooltipEvent event){
//        if (event.getItemStack().hasTagCompound()){
//            String s=event.getItemStack().getTagCompound().toString();
//            event.getToolTip().add(s);
//            //IdlFramework.Log("item %s 's nbt: %s",event.getItemStack().getUnlocalizedName(),s);
//        }
//    }
//}
