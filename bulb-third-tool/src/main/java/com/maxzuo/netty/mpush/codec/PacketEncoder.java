package com.maxzuo.netty.mpush.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * length(4) + type(1) + sessionId(4) + body(n)
 * <p>
 * Created by zfh on 2020/01/05
 */
@ChannelHandler.Sharable
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        encodePacket(msg, out);
    }

    private static void encodePacket (Packet packet, ByteBuf out) {
        if (packet.getType() == Packet.HB_PACKET) {
            out.writeByte(Packet.HB_PACKET_BYTE);
        } else {
            out.writeInt(packet.getBody().length);
            out.writeByte(packet.getType());
            out.writeLong(packet.getSessionId());
            out.writeBytes(packet.getBody());
        }
        packet.setBody(null);
    }
}
