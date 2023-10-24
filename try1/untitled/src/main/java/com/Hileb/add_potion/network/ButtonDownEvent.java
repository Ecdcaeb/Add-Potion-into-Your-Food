package com.Hileb.add_potion.network;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ButtonDownEvent extends PlayerEvent {

    public ButtonDownEvent(Player player) {
        super(player);
    }
}
