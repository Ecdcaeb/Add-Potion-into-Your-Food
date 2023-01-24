package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.IdlFramework;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ProcessMessage implements IMessage {
    public String message;
    public static final String PROCESS="process_request";
    public ProcessMessage(){}
    @Override
    public void fromBytes(ByteBuf buf){
        message= ByteBufUtils.readUTF8String(buf);
    }
    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeUTF8String(buf,message);
    }
    public static class Handler implements IMessageHandler<ProcessMessage,  IMessage> {
        public  IMessage onMessage(final ProcessMessage msg, final MessageContext ctx) {
            MinecraftForge.EVENT_BUS.post(new ProcessEvent(msg));
            return null;
        }
    }
}
