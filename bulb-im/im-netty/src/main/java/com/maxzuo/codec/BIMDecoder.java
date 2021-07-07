package com.maxzuo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class BIMDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(BIMDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        // 标记读取位
        in.markReaderIndex();
        if (in.readByte() != (byte)0x01) {
            ctx.channel().close();
            return;
        }
        int bodyLen = in.readInt();
        if (in.readableBytes() < bodyLen) {
            // 重置到读取位置
            in.resetReaderIndex();
            return;
        }
        byte[] body = new byte[bodyLen];
        in.readBytes(body);
        String msg = new String(body, StandardCharsets.UTF_8);
        logger.debug("BIMDecoder decode msg = {}", msg);
        out.add(msg);
    }
}
