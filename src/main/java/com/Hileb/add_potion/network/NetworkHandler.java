package com.Hileb.add_potion.network;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.gui.potion.expOne.ProcessMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static final ResourceLocation MSG_RESOURCE = new ResourceLocation(IdlFramework.MODID, "msg");

	public static final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(IdlFramework.MODID);

	static int id = 0;
	public static void init()
	{
		//C2S
		channel.registerMessage(ProcessMessage.Handler.class, ProcessMessage.class, id++, Side.SERVER);
		//channel.registerMessage(ContainerDemo.class, ProcessMessage.class, id++, Side.SERVER);
		//just call SendToServer


		//S2C
		//PacketUtil.network.sendTo(new PacketRevenge(cap.isRevengeActive()), (EntityPlayerMP)e.player);
	}

	public static void SendToServer(IMessage packet)
	{
		channel.sendToServer(packet);
	}

	public static void SendToClient(IMessage packet, EntityPlayerMP player)
	{
		channel.sendTo(packet,player);
	}
}
