package com.maxzuo.proto;

import com.maxzuo.proto.protocol.MessagePayload;

import java.time.LocalDateTime;

/**
 * Protocol Buffer 序列化和反序列化
 * <p>
 * Created by zfh on 2019/08/23
 */
public class SimpleSerializationExample {

    public static void main(String[] args) {
        // 序列化 Google Protocol 编码消息
        MessagePayload messagePayload = MessagePayload.newBuilder()
                .setId(1)
                .setContent("dazuo")
                .setType(2)
                .setTime(LocalDateTime.now().toString())
                .build();

        byte[] data = messagePayload.toByteArray();

        try {
            // 反序列化
            MessagePayload message = MessagePayload.parseFrom(data);
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
