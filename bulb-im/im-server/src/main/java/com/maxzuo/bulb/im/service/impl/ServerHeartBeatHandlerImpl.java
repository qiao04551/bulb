package com.maxzuo.bulb.im.service.impl;

import com.maxzuo.bulb.im.config.AppConfiguration;
import com.maxzuo.bulb.im.service.IHeartBeatHandler;
import com.maxzuo.bulb.im.util.NettyAttrUtil;
import com.maxzuo.bulb.im.util.SessionSocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端心跳存活处理
 * <p>
 * Created by zfh on 2019/09/22
 */
@Service("heartBeatHandler")
public class ServerHeartBeatHandlerImpl implements IHeartBeatHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerHeartBeatHandlerImpl.class);

    @Autowired
    private AppConfiguration appConfiguration;

    @Override
    public void process(ChannelHandlerContext ctx) throws Exception {
        long heartBeatTime = appConfiguration.getHeartbeatTime() * 1000;
        Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
        long now = System.currentTimeMillis();

        if (lastReadTime != null && now - lastReadTime > heartBeatTime) {
            String username = SessionSocketHolder.getUserInfo((NioSocketChannel) ctx.channel());
            if (username != null) {
                logger.warn("客户端[{}]心跳超时[{}]ms，需要关闭连接!", username, now - lastReadTime);
                SessionSocketHolder.userOffLine((NioSocketChannel)ctx.channel());
            }
            ctx.channel().close();
        }
    }
}
