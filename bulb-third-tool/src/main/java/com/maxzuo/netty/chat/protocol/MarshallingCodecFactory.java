package com.maxzuo.netty.chat.protocol;

import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * JBoss Marshalling用于对POJO对象进行序列化和反序列化
 * <p>
 * Created by zfh on 2020/01/04
 */
public class MarshallingCodecFactory {

    /**
     * 创建Jboss Marshaller
     */
    public static Marshaller buildMarshalling() throws IOException {
        // serial表示创建Java序列化工厂对象
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return marshallerFactory.createMarshaller(configuration);
    }

    /**
     * 创建Jboss Unmarshaller
     */
    public static Unmarshaller buildUnMarshalling() throws IOException {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        return marshallerFactory.createUnmarshaller(configuration);
    }
}
