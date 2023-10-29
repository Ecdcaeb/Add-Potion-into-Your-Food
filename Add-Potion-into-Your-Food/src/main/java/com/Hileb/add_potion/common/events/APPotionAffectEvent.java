package com.Hileb.add_potion.common.events;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;

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
