package com.maxzuo.model;


/**
 * 通信的消息体
 * <p>
 * Created by zfh on 2019/09/22
 */
public class MessageDTO {

    /**
     * 通信类型 {@link com.maxzuo.constant.Const.CommandType}
     */
    private Integer commandType;

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }
}
