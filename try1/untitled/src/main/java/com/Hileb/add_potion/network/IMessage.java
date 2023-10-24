package com.Hileb.add_potion.network;

import net.minecraft.network.FriendlyByteBuf;

public interface IMessage {
    void read(FriendlyByteBuf buf,IMessage t);
    void write(FriendlyByteBuf buf,IMessage t);
}
