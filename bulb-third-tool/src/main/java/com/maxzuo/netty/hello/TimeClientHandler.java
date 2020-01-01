package com.maxzuo.netty.hello;

import com.maxzuo.proto.protocol.MessagePayload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Created by zfh on 2020/01/01
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接成功");
        /// 发送消息给服务端（字节流）
        // byte[] req = "hello server".getBytes();
        // ByteBuf buf = Unpooled.buffer(req.length);
        // buf.writeBytes(req);
        // ctx.writeAndFlush(buf);

        // protocol编码
        MessagePayload messagePayload = MessagePayload.newBuilder()
                .setId(1)
                .setContent("hello server")
                .setType(2)
                .setTime(LocalDateTime.now().toString())
                .build();
        ctx.writeAndFlush(messagePayload);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /// 字节流
        // ByteBuf buf = (ByteBuf) msg;
        // byte[] req = new byte[buf.readableBytes()];
        // buf.readBytes(req);
        // logger.info("客户端收到消息：{}", new String(req, StandardCharsets.UTF_8));

        // protocol解码
        MessagePayload payload = (MessagePayload) msg;
        logger.info("客户端收到消息：{}", payload.getContent());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("客户端发生异常！", cause);
        // 释放资源
        ctx.close();
    }
}
