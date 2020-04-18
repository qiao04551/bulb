package com.maxzuo.netty.heartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by zfh on 2019/06/09
 */
public class HeartbeatHandlerInitializer extends ChannelInitializer<Channel> {

    private static final int READ_IDEL_TIME_OUT = 4;

    private static final int WRITE_IDEL_TIME_OUT = 5;

    private static final int ALL_IDEL_TIME_OUT = 7;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 使用了 IdleStateHandler ，分别设置了读、写超时的时间
        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
        // 定义了一个 HeartbeatServerHandler 处理器，用来处理超时时，发送心跳
        pipeline.addLast(new HeartbeatServerHandler());
    }
}
