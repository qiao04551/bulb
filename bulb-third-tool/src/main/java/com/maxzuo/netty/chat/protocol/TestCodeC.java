package com.maxzuo.netty.chat.protocol;

import com.maxzuo.netty.chat.message.Header;
import com.maxzuo.netty.chat.message.NettyMessage;
import com.maxzuo.netty.chat.protocol.decoder.MarshallingDecoder;
import com.maxzuo.netty.chat.protocol.encoder.MarshallingEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 编解码测试
 * <p>
 * Created by zfh on 2020/01/05
 */
public class TestCodeC {

    private MarshallingEncoder marshallingEncoder;

    private MarshallingDecoder marshallingDecoder;

    private TestCodeC() throws IOException {
        marshallingDecoder = new MarshallingDecoder();
        marshallingEncoder = new MarshallingEncoder();
    }

    public static void main(String[] args) throws Exception {
        TestCodeC testC = new TestCodeC();
        NettyMessage message = testC.getMessage();
        System.out.println("message: " + message);

        ByteBuf buf = testC.encode(message);
        NettyMessage decodeMsg = testC.decode(buf);
        System.out.println("decodeMsg: " + decodeMsg);
    }

    public NettyMessage getMessage() {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setLength(64);
        header.setSessionID(99999);
        header.setType((byte) 1);
        header.setPriority((byte) 7);
        Map<String, Object> attachment = new HashMap<>();
        attachment.put("name", "dazuo");
        header.setAttachment(attachment);
        nettyMessage.setHeader(header);
        nettyMessage.setBody("abcdefg");
        return nettyMessage;
    }

    private ByteBuf encode(NettyMessage msg) throws Exception {
        ByteBuf sendBuf = Unpooled.buffer();
        Header header = msg.getHeader();
        sendBuf.writeInt(header.getCrcCode());
        sendBuf.writeInt(header.getLength());
        sendBuf.writeLong(header.getSessionID());
        sendBuf.writeByte(header.getType());
        sendBuf.writeByte(header.getPriority());
        sendBuf.writeInt(header.getAttachment().size());
        String key;
        byte[] keyArray;
        Object value;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes(StandardCharsets.UTF_8);
            // key的字节长度
            sendBuf.writeInt(keyArray.length);
            // key的值
            sendBuf.writeBytes(keyArray);

            value = param.getValue();
            marshallingEncoder.encode(value, sendBuf);
        }
        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), sendBuf);
        } else {
            sendBuf.writeInt(0);
        }

        sendBuf.setInt(4, sendBuf.readableBytes());
        return sendBuf;
    }

    private NettyMessage decode(ByteBuf in) throws Exception {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(in.readInt());
        header.setLength(in.readInt());
        header.setSessionID(in.readLong());
        header.setType(in.readByte());
        header.setPriority(in.readByte());

        int size = in.readInt();
        if (size > 0) {
            Map<String, Object> attch = new HashMap<>(size);
            int keySize;
            byte[] keyArray;
            String key;
            for (int i = 0; i < size; i++) {
                // key的长度
                keySize = in.readInt();
                // key的值
                keyArray = new byte[keySize];
                in.readBytes(keyArray);
                key = new String(keyArray, StandardCharsets.UTF_8);

                // key对应的value
                attch.put(key, marshallingDecoder.decode(in));
            }
            header.setAttachment(attch);
        }
        message.setHeader(header);

        if (in.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(in));
        }
        return message;
    }
}
