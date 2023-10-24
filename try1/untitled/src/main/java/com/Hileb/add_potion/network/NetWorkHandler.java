package com.Hileb.add_potion.network;

import com.Hileb.add_potion.AddPotionMain;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.lwjgl.system.windows.MSG;

public class NetWorkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(AddPotionMain.MODID, "main"),
            () -> PROTOCOL_VERSION,
            (s)->true,
            (s)->true
    );
    public static void init(){
        int i=0;
        INSTANCE.registerMessage(i++,ButtonDownMessage.class, MessageHandler::write, MessageHandler::read, ServerMessageHandler::handle);
    }
}
