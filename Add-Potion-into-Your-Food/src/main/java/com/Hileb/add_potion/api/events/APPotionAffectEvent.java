package com.Hileb.add_potion.api.events;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * Dirt BOTH<p></p>
 * Side Server<p></p>
 * Bus {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}<p></p>
 * Cancelable true<p></p>
 * if a living{@link LivingEntity} apply effect.{@link APPotionAffectEvent#getLiving()} <p></p>
 * if a ThrownPotion{@link ThrownPotion} use :{@link APPotionAffectEvent#getThrownPotion()} <p></p>
 * use {@link APPotionAffectEvent#isThrownPotion()} or {@link APPotionAffectEvent#isLiving()} to check<p></p>
 * Canceled: if Living, don't apply.If ThrownPotion , don't make.<p></p><p></p>
 * @author Hileb
 **/
public class APPotionAffectEvent extends EntityEvent {
    public ItemStack food;
    public final boolean isLiving;
    public APPotionAffectEvent(LivingEntity livingEntity, ItemStack foodIn){
        super(livingEntity);
        isLiving=true;
        food=foodIn;
    }
    public APPotionAffectEvent(ThrownPotion thrownPotion, ItemStack foodIn){
        super(thrownPotion);
        isLiving=false;
        food=foodIn;
    }
    @Override
    public boolean isCancelable() {
        return true;
    }
    public boolean isThrownPotion(){return !isLiving;}
    public boolean isLiving(){return isLiving;};

    public ThrownPotion getThrownPotion(){
        return (ThrownPotion)getEntity();
    }
    public LivingEntity getLiving(){
        return (LivingEntity)getEntity();
    }
}
