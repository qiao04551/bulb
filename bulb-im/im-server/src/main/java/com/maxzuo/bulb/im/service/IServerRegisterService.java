package com.maxzuo.bulb.im.service;

/**
 * 服务信息注册
 * <p>
 * Created by zfh on 2019/09/22
 */
public interface IServerRegisterService {

    /**
     * 服务上线注册zookeeper，服务注册
     * @param ip   IP地址
     * @param port 服务端口
     */
    void serverOnlineRegiste (String ip, Integer port);
}
