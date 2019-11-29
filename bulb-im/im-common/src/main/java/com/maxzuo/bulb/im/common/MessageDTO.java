package com.maxzuo.bulb.im.common;

import com.maxzuo.bulb.im.constant.Const;

/**
 * 通信的消息体
 * <p>
 * Created by zfh on 2019/09/22
 */
public class MessageDTO {

    /**
     * 通信类型 {@link Const.CommandType}
     */
    private Integer commandType;

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }
}
