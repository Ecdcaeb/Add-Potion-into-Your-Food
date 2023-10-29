package com.Hileb.add_potion.common.init;

import com.Hileb.add_potion.common.events.APPotionAffectEvent;
import com.Hileb.add_potion.common.potion.APotion;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.common.potion.PotionUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collection;
import java.util.List;

public class OnEat {
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event){
        Level world=event.getEntity().level();
        Player player=event.getEntity() instanceof Player?(Player) event.getEntity():null;
        if (!world.isClientSide){
            if (ApplyUtil.canApplyStackAsFoods(player,event.getItem())){
                if (!MinecraftForge.EVENT_BUS.post(new APPotionAffectEvent(event.getEntity(),event.getItem()))){
                    addAPEffectForLiving(event.getEntity(),event.getItem());
                }
            }
        }
    }
    public static void addAPEffectForLiving(LivingEntity living, ItemStack stack){
        List<APotion> list3= PotionUtil.BuildInUtils.getInternalAPEffect(stack);
        for(APotion potion:list3){
            living.addEffect(potion.getEffect());
        }
    }
    @SubscribeEvent
    public static void addPotionIntoEntityPotion(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof ThrownPotion entity && !event.getLevel().isClientSide){
            ItemStack stack=entity.getItem();
            if (!MinecraftForge.EVENT_BUS.post(new APPotionAffectEvent(entity,stack))){
                Collection<MobEffectInstance> collection=PotionUtil.getListOfPotionEffect(PotionUtil.BuildInUtils.getInternalAPEffect(stack));
                collection.addAll(PotionUtils.getCustomEffects(stack));
                PotionUtils.setCustomEffects(stack,collection);
            }
        }
    }
}
