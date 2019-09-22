package com.maxzuo.bulb.im.service;

import com.maxzuo.bulb.im.common.ServerOnlineInfo;

/**
 * 服务发现Server
 * <p>
 * Created by zfh on 2019/09/22
 */
public interface IServerDiscoveryService {

    /**
     * 获取一条服务器信息
     */
    ServerOnlineInfo obtainServerInfo ();
}
