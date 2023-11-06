package com.Hileb.add_potion.common.init;

import com.Hileb.add_potion.common.potion.APEventFactory;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.common.potion.PotionUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
        if (!event.getLevel().isClientSide && event.getEntity() instanceof ThrownPotion entity){
            ItemStack stack=entity.getItem();
            APEventFactory.onThrowableHandle(entity,stack,apPotionAffectEvent -> {
                Collection<MobEffectInstance> collection=PotionUtil.getListOfPotionEffect(PotionUtil.BuildInUtils.getInternalAPEffect(apPotionAffectEvent.food));
                collection.addAll(PotionUtils.getCustomEffects(apPotionAffectEvent.food));
                PotionUtils.setCustomEffects(stack,collection);
            });
        }
    }
    @SubscribeEvent
    public static void onPlayerFeedAnimal(PlayerInteractEvent.EntityInteract event){
        Level level=event.getLevel();
        if (!level.isClientSide){
            if (event.getTarget() instanceof Animal animal){
                if (animal.isFood(event.getItemStack())) {
                    APEventFactory.onLivingEaten(animal,event.getItemStack(),apPotionAffectEvent -> {
                        PotionUtil.BuildInUtils.getInternalAPEffect(apPotionAffectEvent.food).
                                forEach(aPotion ->animal.addEffect(aPotion.getEffect()));
                    });
                }
            }
        }
    }
}
