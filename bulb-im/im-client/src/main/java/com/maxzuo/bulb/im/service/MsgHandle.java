package com.maxzuo.bulb.im.service;

/**
 * Created by zfh on 2019/11/29
 */
public interface MsgHandle {

    /**
     * 用户登录
     */
    void login(String line);

    /**
     * 发送消息给指定用户
     */
    void sendMsg (String line);
}
