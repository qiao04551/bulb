package com.maxzuo.netty.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端的处理类
 * <p>
 * Created by zfh on 2019/06/09
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 将读取的消息打印出来
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
