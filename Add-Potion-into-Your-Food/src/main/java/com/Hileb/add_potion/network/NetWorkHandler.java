package com.Hileb.add_potion.network;

import com.Hileb.add_potion.AddPotion;
import com.Hileb.add_potion.common.container.ContainerAP;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class NetWorkHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(AddPotion.MODID,"main"))
            .clientAcceptedVersions((status, version) -> true)
            .serverAcceptedVersions((status, version) ->true)
            .acceptedVersions((status, version) -> true)
            .optional().simpleChannel()
            //APMessage.class
            .messageBuilder(APMessage.class, NetworkDirection.PLAY_TO_SERVER)
            .decoder(buf -> new APMessage())
            .encoder((message, buf) -> {})
            .consumerMainThread((apMessage, context) -> {
                ServerPlayer player = context.getSender();
                if (player.containerMenu instanceof ContainerAP) {
                    ((ContainerAP) player.containerMenu).handleMessage();
                }
            })
            //message end
            .add();
    public static void init(){
    }
}
