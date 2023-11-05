package com.Hileb.add_potion.common.init;

import com.Hileb.add_potion.common.potion.APEventFactory;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.common.potion.PotionUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collection;

public class OnEat {
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event){
        LivingEntity entity=event.getEntity();
        if (!entity.level().isClientSide){
            ItemStack stack=event.getItem();
            if (ApplyUtil.canApplyStackAsFoods(entity instanceof Player?(Player)entity:null,stack)){
                APEventFactory.onLivingEaten(entity,stack,apPotionAffectEvent -> {
                    PotionUtil.BuildInUtils.getInternalAPEffect(apPotionAffectEvent.food).
                            forEach(aPotion -> entity.addEffect(aPotion.getEffect()));
                });
            }
        }
    }
    @SubscribeEvent
    public static void addPotionIntoEntityPotion(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof ThrownPotion entity && !event.getLevel().isClientSide){
            ItemStack stack=entity.getItem();
            APEventFactory.onThrowableHandle(entity,stack,apPotionAffectEvent -> {
                Collection<MobEffectInstance> collection=PotionUtil.getListOfPotionEffect(PotionUtil.BuildInUtils.getInternalAPEffect(apPotionAffectEvent.food));
                collection.addAll(PotionUtils.getCustomEffects(stack));
                PotionUtils.setCustomEffects(stack,collection);
            });
        }
    }
}
