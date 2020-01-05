package com.maxzuo.netty.chat.protocol.encoder;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

/**
 * Created by zfh on 2020/01/04
 */
public class ChannelBufferByteOutput implements ByteOutput {

    private final ByteBuf byteBuf;

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public ChannelBufferByteOutput(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public void write(int i) throws IOException {
        byteBuf.writeByte(i);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        byteBuf.writeBytes(bytes);
    }

    @Override
    public void write(byte[] bytes, int srcIndex, int length) throws IOException {
        byteBuf.writeBytes(bytes, srcIndex, length);
    }

    @Override
    public void close() throws IOException {
        // Nothing to do
    }

    @Override
    public void flush() throws IOException {
        // Nothing to do
    }
}
