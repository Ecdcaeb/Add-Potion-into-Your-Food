package com.Hileb.add_potion.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerMessageHandler {
    public static void handle(ButtonDownMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
            MinecraftForge.EVENT_BUS.post(new ButtonDownEvent(sender));
        });
        ctx.get().setPacketHandled(true);
    }
}
