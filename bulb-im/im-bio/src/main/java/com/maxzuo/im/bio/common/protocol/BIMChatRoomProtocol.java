package com.maxzuo.im.bio.common.protocol;

import com.maxzuo.im.bio.common.entity.Message;

import java.io.*;

/**
 * 聊天室-通信协议
 * <p>
 * Created by zfh on 2019/11/30
 */
public class BIMChatRoomProtocol {

    /**
     * 消息序列化
     */
    public static void write(OutputStream os, Message message) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject(message);
    }

    /**
     * 消息反序列化
     */
    public static Message parse(InputStream is) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(is);
        Message message;
        try {
            message = (Message) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new Error(e.getMessage());
        }
        return message;
    }
}
