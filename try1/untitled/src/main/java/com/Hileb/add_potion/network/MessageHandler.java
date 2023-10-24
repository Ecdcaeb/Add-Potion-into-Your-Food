package com.Hileb.add_potion.network;

import net.minecraft.network.FriendlyByteBuf;

public class MessageHandler {
    public static void write(Object message, FriendlyByteBuf buf){

    }
    public static void read(FriendlyByteBuf buf,Object message){

    }

    public static ButtonDownMessage read(FriendlyByteBuf var1) {
        return new ButtonDownMessage();
    }
}
