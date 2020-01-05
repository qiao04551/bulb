package com.maxzuo.netty.marshalling.client;

import com.maxzuo.netty.marshalling.pojo.SubscribeReq;
import com.maxzuo.netty.marshalling.pojo.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zfh on 2020/01/05
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SubReqClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接成功");

        SubscribeReq req = new SubscribeReq();
        req.setSubReqID(1);
        req.setUserName("dazuo");
        req.setProductName("book");
        ctx.writeAndFlush(req);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeResp resp = (SubscribeResp) msg;
        logger.info("客户端收到消息：{}", resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
