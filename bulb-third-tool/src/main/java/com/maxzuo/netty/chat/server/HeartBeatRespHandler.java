package com.maxzuo.netty.chat.server;

import com.maxzuo.netty.chat.message.Header;
import com.maxzuo.netty.chat.message.MessageType;
import com.maxzuo.netty.chat.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳响应
 * <p>
 * Created by zfh on 2020/01/04
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatRespHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage)msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ) {
            logger.info("receive client heart beat message：{}", msg);

            // 心跳响应
            NettyMessage heartBeat = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_RESP);
            heartBeat.setHeader(header);

            ctx.writeAndFlush(heartBeat);
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
