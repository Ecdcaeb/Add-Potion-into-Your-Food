package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.event.APPotionAffectEvent;
import com.Hileb.add_potion.util.Reference;
import com.Hileb.add_potion.util.potion.APotion;
import com.Hileb.add_potion.util.potion.ApplyUtil;
import com.Hileb.add_potion.util.potion.PotionUtil;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ThrowableImpactEvent;
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
    @SubscribeEvent
    public static void onThrowPotion(ThrowableImpactEvent event){
        //potion
        if (event.getEntityThrowable() instanceof EntityPotion){
            EntityPotion entityThrowable=(EntityPotion) event.getEntityThrowable();
            if (!entityThrowable.world.isRemote){
                if (entityThrowable.getPotion().getItem() instanceof ItemSplashPotion){
                    if (event.getRayTraceResult().typeOfHit== RayTraceResult.Type.ENTITY){
                        if(event.getRayTraceResult().entityHit instanceof EntityLivingBase){
                            doit((EntityLivingBase) event.getRayTraceResult().entityHit,entityThrowable.getPotion());
                        }
                    }
                }
                if (entityThrowable.getPotion().getItem() instanceof ItemLingeringPotion){
                    doitArea(entityThrowable,entityThrowable.getPotion());
                }
            }
        }
        //
    }
    private static void doit(EntityLivingBase player, ItemStack stack){
        List<APotion> list3=PotionUtil.getAllEffectIShouldDo(stack);
        for(APotion potion:list3){
            player.addPotionEffect(potion.getEffect());
        }
    }
    private static void doitArea(EntityPotion entityThrowable, ItemStack stack){
        List<APotion> list3=PotionUtil.getAllEffectIShouldDo(stack);
        makeAreaOfEffectCloud(entityThrowable,entityThrowable.getPotion(),list3);
    }
    private static void makeAreaOfEffectCloud(EntityThrowable throwable,ItemStack p_190542_1_, List<APotion> aPotions)
    {
        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(throwable.world, throwable.posX, throwable.posY, throwable.posZ);
        entityareaeffectcloud.setOwner(throwable.getThrower());
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.setPotion(PotionUtil.makeType(aPotions));

        for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromItem(p_190542_1_))
        {
            entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
        }

        NBTTagCompound nbttagcompound = p_190542_1_.getTagCompound();

        if (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99))
        {
            entityareaeffectcloud.setColor(nbttagcompound.getInteger("CustomPotionColor"));
        }

        throwable.world.spawnEntity(entityareaeffectcloud);
    }
}
