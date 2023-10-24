package com.Hileb.add_potion.common.event;



import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import java.util.List;

public class APTooltipEvent extends Event {
    public List<Component> stringList;
    public ItemStack stack;
    public APTooltipEvent(@Nonnull List<Component> tooltip, ItemStack stackIn){
        stringList=tooltip;
        stack=stackIn;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
