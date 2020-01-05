package com.maxzuo.netty.mpush.codec;

import java.util.Arrays;

/**
 * 数据包
 * <p>
 * Created by zfh on 2020/01/05
 */
public class Packet {

    public static final int HEADER_LEN = 9;

    public static final byte HB_PACKET = 1;

    public static final byte HB_PACKET_BYTE = -33;

    private byte type;

    private long sessionId;

    private byte[] body;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "type=" + type +
                ", sessionId=" + sessionId +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
