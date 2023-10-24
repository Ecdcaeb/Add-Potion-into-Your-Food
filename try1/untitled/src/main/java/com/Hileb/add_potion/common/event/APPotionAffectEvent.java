package com.Hileb.add_potion.common.event;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class APPotionAffectEvent extends LivingEvent {
    public ItemStack food;
    public APPotionAffectEvent(LivingEntity player, ItemStack foodIn){
        super(player);
        food=foodIn;
    }
    @Override
    public boolean isCancelable() {
        return true;
    }
}
