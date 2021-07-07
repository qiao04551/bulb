package com.maxzuo.server;

import com.maxzuo.util.NettyAttrUtil;
import com.maxzuo.util.SessionSocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端心跳存活处理
 * <p>
 * Created by zfh on 2019/09/22
 */
public class HeartBeatRespHandler {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatRespHandler.class);

    /**
     * 心跳间隔，单位：ms
     */
    private static final Integer heartBeatTime = 10000;

    public static void process(ChannelHandlerContext ctx) {
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
