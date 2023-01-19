package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.event.APPotionAffectEvent;
import com.Hileb.add_potion.util.Reference;
import com.Hileb.add_potion.util.potion.APotion;
import com.Hileb.add_potion.util.potion.ApplyUtil;
import com.Hileb.add_potion.util.potion.PotionUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class OnEat {
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event){
        World world=event.getEntityLiving().world;
        if (!world.isRemote){
            if (ApplyUtil.applyFood(event.getItem())){
                if (!MinecraftForge.EVENT_BUS.post(new APPotionAffectEvent(event.getEntityLiving(),event.getItem()))){
                    doit(event.getEntityLiving(),event.getItem());
                }
            }
        }
    }
    private static void doit(EntityLivingBase player, ItemStack stack){
        List<APotion> list3=PotionUtil.getAllEffectIShouldDo(stack);
        for(APotion potion:list3){
            player.addPotionEffect(potion.getEffect());
        }
    }
}
