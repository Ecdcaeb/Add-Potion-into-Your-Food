package com.Hileb.add_potion.common.init;

import com.Hileb.add_potion.AddPotionMain;
import com.Hileb.add_potion.common.event.APPotionAffectEvent;
import com.Hileb.add_potion.common.potion.APotion;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.common.potion.PotionUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mod.EventBusSubscriber(modid = AddPotionMain.MODID)
public class OnEat {
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event){
        Level world=event.getEntity().getLevel();
        if (!world.isClientSide){
            if (ApplyUtil.canApplyStackAsFoods(event.getItem())){
                if (!MinecraftForge.EVENT_BUS.post(new APPotionAffectEvent(event.getEntity(),event.getItem()))){
                    addAPEffectForLiving(event.getEntity(),event.getItem());
                }
            }
        }
    }
    public static void addAPEffectForLiving(LivingEntity player, ItemStack stack){
        List<APotion> list3= PotionUtil.getAllEffectIShouldDo(stack);
        for(APotion potion:list3){
            player.addEffect(potion.getEffect());
        }
    }
    @SubscribeEvent
    public static void addPotionIntoEntityPotion(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof ThrownPotion && !event.getLevel().isClientSide){
            ThrownPotion entity=(ThrownPotion)event.getEntity();
            ItemStack stack=entity.getItem();
            List<APotion> aPotions=PotionUtil.getAllEffectIShouldDo(stack);
            Collection<MobEffectInstance> collection=PotionUtil.getListOfPotionEffect(aPotions);
            PotionUtils.setCustomEffects(stack,collection);
        }
    }
}
