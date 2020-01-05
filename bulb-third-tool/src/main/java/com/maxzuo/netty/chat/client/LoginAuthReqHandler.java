package com.maxzuo.netty.chat.client;

import com.maxzuo.netty.chat.message.Header;
import com.maxzuo.netty.chat.message.MessageType;
import com.maxzuo.netty.chat.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录请求
 * <p>
 * Created by zfh on 2020/01/04
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginAuthReqHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接成功");

        // 建立连接后，发送认证消息
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ);
        message.setHeader(header);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage)msg;
        // 若是握手应答消息，判断是否认证成功
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP) {
            byte loginResult= (byte)message.getBody();
            if (loginResult != 0) {
                // 握手失败，关闭连接
                ctx.close();
            }else {
                logger.info("客户端登录成功：{}", message);
                ctx.fireChannelRead(msg);
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
