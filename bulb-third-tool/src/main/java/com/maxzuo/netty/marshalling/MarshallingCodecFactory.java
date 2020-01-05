package com.maxzuo.netty.marshalling;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import java.io.IOException;

/**
 * JBoss Marshalling是一个Java对象序列化包，对JDK默认的序列化框架进行了优化，但又保持跟java.io.Serialize接口的兼容，
 * 同时增加了一些可调的参数和附加的特性。
 * <p>
 * Created by zfh on 2020/01/04
 */
public class MarshallingCodecFactory {

    /**
     * 创建Jboss解码器
     */
    public static MarshallingDecoder buildMarshallingDecoder() throws IOException {
        // serial表示创建Java序列化工厂对象
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        DefaultUnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        return new MarshallingDecoder(provider);
    }

    /**
     * 创建Jboss编码器
     */
    public static MarshallingEncoder buildMarshallingEncoder() throws IOException {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        DefaultMarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        return new MarshallingEncoder(provider);
    }
}
