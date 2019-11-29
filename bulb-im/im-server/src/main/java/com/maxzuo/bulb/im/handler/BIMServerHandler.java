package com.maxzuo.bulb.im.handler;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.bulb.im.common.ChatMessageDTO;
import com.maxzuo.bulb.im.common.LoginMessageDTO;
import com.maxzuo.bulb.im.common.MessageDTO;
import com.maxzuo.bulb.im.common.HealthMessageDTO;
import com.maxzuo.bulb.im.constant.Const;
import com.maxzuo.bulb.im.service.IHeartBeatHandler;
import com.maxzuo.bulb.im.util.NettyAttrUtil;
import com.maxzuo.bulb.im.util.SessionSocketHolder;
import com.maxzuo.bulb.im.util.SpringBeanFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static com.maxzuo.bulb.im.util.SessionSocketHolder.getChannel;

/**
 * Netty 消息handler
 * Created by zfh on 2019/09/22
 */
@ChannelHandler.Sharable
public class BIMServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(BIMServerHandler.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端离线触发！");
        // 可能出现业务判断离线后再次触发 channelInactive
        String username = SessionSocketHolder.getUserInfo((NioSocketChannel) ctx.channel());
        if (username != null){
            logger.warn("[{}]触发 channelInactive 掉线!", username);
            SessionSocketHolder.userOffLine((NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                logger.info("定时检测，客户端是否存活！");
                IHeartBeatHandler heartBeatHandler = SpringBeanFactory.getBean(IHeartBeatHandler.class);
                heartBeatHandler.process(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (!StringUtils.isEmpty(msg)){
            MessageDTO messageDTO = JSONObject.parseObject(msg, MessageDTO.class);
            switch (messageDTO.getCommandType()) {
                case Const.CommandType.LOGIN:
                    LoginMessageDTO loginDTO = JSONObject.parseObject(msg, LoginMessageDTO.class);
                    logger.info("用户登录 uesrname = {}", loginDTO.getUsername());
                    SessionSocketHolder.userOnline(loginDTO.getUsername(), (NioSocketChannel) ctx.channel());
                    break;
                case Const.CommandType.MSG:
                    // 点对点发送
                    ChatMessageDTO chatMsg = JSONObject.parseObject(msg, ChatMessageDTO.class);
                    NioSocketChannel channel = getChannel(chatMsg.getTo());
                    if (channel == null) {
                        logger.warn("用户已下线无法发送消息 to = {}", chatMsg.getTo());
                        return;
                    }
                    sendMsg(channel, msg.getBytes());
                    break;
                case Const.CommandType.PING:
                    HealthMessageDTO healthDTO = JSONObject.parseObject(msg, HealthMessageDTO.class);
                    logger.debug("客户端心跳检测 time = {} payLoad = {}", LocalDateTime.now(), healthDTO.getPayLoad());
                    HealthMessageDTO response = new HealthMessageDTO();
                    response.setCommandType(Const.CommandType.PING);
                    response.setPayLoad("PONG");
                    byte[] content = JSONObject.toJSONBytes(response);

                    sendMsg((NioSocketChannel) ctx.channel(), content);
                    break;
                default:
            }
        }
    }

    /**
     * 统一发送消息
     * @param channel 连接
     * @param content 消息
     */
    private void sendMsg (NioSocketChannel channel, byte[] content) {
        NettyAttrUtil.updateReaderTime(channel,System.currentTimeMillis());
        ByteBuf message = Unpooled.buffer(content.length);
        message.writeBytes(content);
        ChannelFuture future = channel.writeAndFlush(message);
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (!channelFuture.isSuccess()) {
                logger.error("IO error，close Channel");
                future.channel().close();
            }
        });
    }

    /**
     * 当Netty由于IO错误或者处理器在处理事件时抛出异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
