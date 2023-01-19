package com.Hileb.add_potion.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class APPotionAffectEvent extends LivingEvent {
    public ItemStack food;
    public APPotionAffectEvent(EntityLivingBase player, ItemStack foodIn){
        super(player);
        food=foodIn;
    }
    @Override
    public boolean isCancelable() {
        return true;
    }
}
