package com.Hileb.add_potion.network;

import io.netty.buffer.*;
import net.minecraft.network.FriendlyByteBuf;
import org.lwjgl.system.windows.MSG;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public class ButtonDownMessage implements IMessage{



    public ButtonDownMessage() {

    }

    @Override
    public void read(FriendlyByteBuf buf, IMessage t) {

    }

    @Override
    public void write(FriendlyByteBuf buf, IMessage t) {

    }
}
