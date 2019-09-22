package com.maxzuo.bulb.im.service.impl;

import com.maxzuo.bulb.im.client.BIMClient;
import com.maxzuo.bulb.im.service.IHeartBeatHandler;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zfh on 2019/09/22
 */
@Service("heartBeatHandler")
public class ClientHeartBeatHandlerImpl implements IHeartBeatHandler {

    @Autowired
    private BIMClient bimClient;

    @Override
    public boolean processDisconnect(ChannelHandlerContext ctx) throws Exception {
        return bimClient.reconnect();
    }
}
