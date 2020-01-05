package com.maxzuo.netty.chat.message;

/**
 * 消息类型
 * <p>
 * Created by zfh on 2020/01/04
 */
public class MessageType {

    /**
     * 业务请求消息
     */
    public static final byte BUSINESS_REQ = 0;

    /**
     * 业务响应消息
     */
    public static final byte BUSINESS_RESP = 1;

    /**
     * 握手请求消息
     */
    public static final byte LOGIN_REQ = 3;

    /**
     * 握手应答消息
     */
    public static final byte LOGIN_RESP = 4;

    /**
     * 心跳请求消息
     */
    public static final byte HEARTBEAT_REQ = 5;

    /**
     * 心跳应答消息
     */
    public static final byte HEARTBEAT_RESP = 6;
}
