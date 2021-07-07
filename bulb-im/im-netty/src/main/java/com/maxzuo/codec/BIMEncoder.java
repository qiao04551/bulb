package com.maxzuo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 通信协议
 * ----------------------------------------
 * | Magic(1字节) | len(4字节) | body(len) |
 * ---------------------------------------
 */
public class BIMEncoder extends MessageToByteEncoder<ByteBuf> {

    private static final Logger logger = LoggerFactory.getLogger(BIMEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf buf, ByteBuf out) throws Exception {
        String msg = buf.toString(StandardCharsets.UTF_8);
        logger.debug("BIMEncoder encode msg = {}", msg);
        byte[] body = msg.getBytes(StandardCharsets.UTF_8);
        out.writeByte((byte)0x01);
        out.writeInt(body.length);
        out.writeBytes(body);
    }
}
