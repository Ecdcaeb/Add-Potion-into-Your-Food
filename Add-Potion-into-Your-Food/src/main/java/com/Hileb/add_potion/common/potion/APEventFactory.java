package com.Hileb.add_potion.common.potion;

import com.Hileb.add_potion.api.events.APPotionAffectEvent;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

import java.util.function.Consumer;

/**
 * @Project Add-Potion-into-Your-Food
 * @Author Hileb
 * @Date 2023/11/4 17:16
 **/
public class APEventFactory {
    public static <T extends Event> InteractionResultHolder<T> fire(T event) {
        return MinecraftForge.EVENT_BUS.post(event)?InteractionResultHolder.fail(event):InteractionResultHolder.success(event);
    }
    public static <T extends Event> void fireAndHandle(T event, Consumer<T> success,Consumer<T> fail){
        if (MinecraftForge.EVENT_BUS.post(event))fail.accept(event);
        else success.accept(event);
    }
    public static void onLivingEaten(LivingEntity entity, ItemStack stack,Consumer<APPotionAffectEvent> success){
        fireAndHandle(new APPotionAffectEvent(entity, stack), success, event -> {});
    }
    public static void onThrowableHandle(ThrownPotion entity,ItemStack stack,Consumer<APPotionAffectEvent> success){
        fireAndHandle(new APPotionAffectEvent(entity, stack), success, event -> {});
    }

}
