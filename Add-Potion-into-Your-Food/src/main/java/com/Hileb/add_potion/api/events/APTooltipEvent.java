package com.Hileb.add_potion.api.events;



import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Dirt CLIENT<p></p>
 * Side Client<p></p>
 * Bus {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}<p></p>
 * Cancelable true<p></p>
 * fire when tooltip<p></p><p></p>
 * @author Hileb
 **/
@Cancelable
public class APTooltipEvent extends Event {
    public List<Component> stringList;
    public ItemStack stack;
    public APTooltipEvent(@Nonnull List<Component> tooltip, ItemStack stackIn){
        stringList=tooltip;
        stack=stackIn;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    @Override
    public boolean hasResult() {
        return false;
    }
}
