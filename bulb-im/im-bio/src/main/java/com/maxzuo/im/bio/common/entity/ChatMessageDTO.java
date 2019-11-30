package com.maxzuo.im.bio.common.entity;

/**
 * 聊天室通信实体
 * <p>
 * Created by zfh on 2019/11/29
 */
public class ChatMessageDTO {

    private String from;

    private String to;

    private String msg;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
