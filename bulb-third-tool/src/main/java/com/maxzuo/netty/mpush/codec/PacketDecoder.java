package com.maxzuo.netty.mpush.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * 解码器
 * length(4) + type(1) + sessionId(4) + body(n)
 * <p>
 * Created by zfh on 2020/01/05
 */
public class PacketDecoder extends ByteToMessageDecoder {

    private static final int maxPacketSize = 10 * 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        docodeHeartBeat(in, out);
        decodeFrames(in, out);
    }

    private void docodeHeartBeat (ByteBuf in, List<Object> out) {
        while (in.isReadable()) {
            if (in.readByte() == Packet.HB_PACKET_BYTE) {
                Packet packet = new Packet();
                packet.setType(Packet.HB_PACKET);
                out.add(packet);
            } else {
                in.readerIndex(in.readerIndex() - 1);
                break;
            }
        }
    }

    private void decodeFrames (ByteBuf in, List<Object> out) {
        if (in.readableBytes() >= Packet.HEADER_LEN) {
            // 1.记录当前读取位置位置.如果读取到非完整的frame,要恢复到该位置,便于下次读取
            in.markReaderIndex();

            Packet packet = decodeFrame(in);
            if (packet != null) {
                out.add(packet);
            } else {
                // 2.读取到不完整的frame，恢复到最近一次正常读取的位置,便于下次读取
                in.resetReaderIndex();
            }
        }
    }

    private Packet decodeFrame (ByteBuf in) {
        int readableBytes = in.readableBytes();
        int bodyLen = in.readInt();
        if (readableBytes < (bodyLen + Packet.HEADER_LEN)) {
            return null;
        }
        if (bodyLen > maxPacketSize) {
            throw new TooLongFrameException("packet length over limit: " + maxPacketSize);
        }
        return decodePacket(in, bodyLen);
    }

    private Packet decodePacket (ByteBuf in, int bodyLen) {
        Packet packet = new Packet();
        packet.setType(in.readByte());
        packet.setSessionId(in.readLong());
        if (bodyLen > 0) {
            byte[] bytes = new byte[bodyLen];
            in.readBytes(bytes);
            packet.setBody(bytes);
        }
        return packet;
    }
}
