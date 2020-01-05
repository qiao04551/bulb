package com.maxzuo.netty.chat.protocol.encoder;

import com.maxzuo.netty.chat.protocol.MarshallingCodecFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * 消息编码工具类
 * <p>
 * Created by zfh on 2020/01/04
 */
public class MarshallingEncoder {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    private Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    /**
     * 使用marshall对Object进行编码，并且写入bytebuf...
     */
    public void encode(Object msg, ByteBuf out) throws IOException {
        try {
            // 1.获取写入位置
            int lengthPos = out.writerIndex();
            // 2.先写入4个byte，用于记录Object对象编码后长度（预设长度）
            out.writeBytes(LENGTH_PLACEHOLDER);
            // 3.使用代理对象，防止marshaller写完之后关闭byte buf
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            // 4.开始编码
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();

            // 5.设置对象长度（用真实长度覆盖预设长度）
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } catch (Exception e) {
            marshaller.close();
        }
    }
}
