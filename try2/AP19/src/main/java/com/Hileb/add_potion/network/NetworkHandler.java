package com.Hileb.add_potion.network;

import com.Hileb.add_potion.AP19Main;
import com.Hileb.add_potion.gui.potion.expOne.ProcessMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final String VISION="1";
    public static final ResourceLocation MSG_RESOURCE = new ResourceLocation(AP19Main.MODID, "msg");

    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(MSG_RESOURCE,()->VISION,VISION::equals,VISION::equals)//.INSTANCE.newSimpleChannel(IdlFramework.MODID);

    static int id = 0;
    public static void init()
    {
        channel.registerMessage(id++,)
        //C2S
//        channel.registerMessage()
//        channel.registerMessage(ProcessMessage.Handler.class, ProcessMessage.class, id++, Side.SERVER);
//        //channel.registerMessage(ContainerDemo.class, ProcessMessage.class, id++, Side.SERVER);
//        //just call SendToServer


        //S2C
        //PacketUtil.network.sendTo(new PacketRevenge(cap.isRevengeActive()), (EntityPlayerMP)e.player);
    }

//    public static void SendToServer(Message packet)
//    {
//        channel.sendToServer(packet);
//    }
//
//    public static void SendToClient(IMessage packet, EntityPlayerMP player)
//    {
//        channel.sendTo(packet,player);
//    }
}
