package com.maxzuo.netty.marshalling.server;

import com.maxzuo.netty.marshalling.pojo.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zfh on 2020/01/05
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SubReqServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("服务端收到消息：{}", msg);

        // 响应客户端
        SubscribeResp resp = new SubscribeResp();
        resp.setCode(1);
        resp.setMsg("ok");
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
