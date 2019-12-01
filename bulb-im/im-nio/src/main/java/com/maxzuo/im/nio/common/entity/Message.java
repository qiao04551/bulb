package com.maxzuo.im.nio.common.entity;

import com.maxzuo.im.nio.common.constant.Const;

import java.io.Serializable;

/**
 * 通信的消息体
 * <p>
 * Created by zfh on 2019/09/22
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -7917787251118847810L;

    /**
     * 通信类型 {@link Const.CommandType}
     */
    private Integer commandType;

    /**
     * 消息载体
     */
    private String payLoad;

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    @Override
    public String toString() {
        return "Message{" +
                "commandType=" + commandType +
                ", payLoad='" + payLoad + '\'' +
                '}';
    }
}
