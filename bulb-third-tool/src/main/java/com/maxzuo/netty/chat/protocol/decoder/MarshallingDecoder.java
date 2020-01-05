package com.maxzuo.netty.chat.protocol.decoder;

import com.maxzuo.netty.chat.protocol.MarshallingCodecFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * 消息解码工具类
 * <p>
 * Created by zfh on 2020/01/04
 */
public class MarshallingDecoder {

    private final Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    public Object decode(ByteBuf in) throws Exception {
        // 1.读取4个byte，里面放置的是object对象的byte长度
        int objectSize = in.readInt();
        // 返回缓存区的一个切片，修改返回的缓冲区或这个缓冲区的内容会影响彼此的内容，同时它们维护独立的索引和标记。
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        // 2.使用bytebuf的代理类
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            // 3.开始解码
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();

            // 4.读完之后设置读取的位置
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        } finally {
            unmarshaller.close();
        }
    }
}
