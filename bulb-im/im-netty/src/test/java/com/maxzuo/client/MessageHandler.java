package com.maxzuo.client;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.constant.Const;
import com.maxzuo.model.ChatMessageDTO;
import com.maxzuo.model.HealthMessageDTO;
import com.maxzuo.model.MessageDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalTime;

/**
 * 客户端消息处理
 * <p>
 * Created by zfh on 2019/09/22
 */
public class MessageHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    /**
     * 客户端发送心跳
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;
            // 定时检测服务端是否存活，发送心跳
            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                HealthMessageDTO messageDTO =  new HealthMessageDTO();
                messageDTO.setCommandType(Const.CommandType.PING);
                messageDTO.setPayLoad("PING");
                byte[] content = JSONObject.toJSONBytes(messageDTO);

                ByteBuf message = Unpooled.buffer(content.length);
                message.writeBytes(content);

                ctx.writeAndFlush(message).addListeners((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        logger.error("IO error，close Channel");
                        future.channel().close();
                    }
                }) ;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接成功!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端断开了连接!");
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (!StringUtils.isEmpty(msg)){
            MessageDTO messageDTO = JSONObject.parseObject(msg, MessageDTO.class);
            switch (messageDTO.getCommandType()) {
                case Const.CommandType.MSG:
                    ChatMessageDTO chatDTO = JSONObject.parseObject(msg, ChatMessageDTO.class);
                    logger.info("收到消息 sendUser = {} msg = {}", chatDTO.getFrom(), chatDTO.getMsg());
                    break;
                case Const.CommandType.PING:
                    HealthMessageDTO ppDTO = JSONObject.parseObject(msg, HealthMessageDTO.class);
                    logger.debug("服务器心跳响应 time = {} payLoad = {}", LocalTime.now(), ppDTO.getPayLoad());
                    break;
                default:
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常时断开连接
        cause.printStackTrace() ;
        ctx.close() ;
    }
}
