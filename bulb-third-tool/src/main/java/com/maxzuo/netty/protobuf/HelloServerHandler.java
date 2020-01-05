package com.maxzuo.netty.protobuf;

import com.maxzuo.proto.protocol.MessagePayload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * 输出接收到的消息
 *
 * Created by zfh on 2019/06/08
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HelloServerHandler.class);

    /**
     * 收到数据时调用
     * @param ctx 通道处理的上下文信息
     * @param msg 接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            /// 字节流
            // ByteBuf buf = (ByteBuf) msg;
            // byte[] req = new byte[buf.readableBytes()];
            // buf.readBytes(req);
            // logger.info("服务端收到消息：{}", new String(req, StandardCharsets.UTF_8));

            /// 响应客户端（字节流）
            // byte[] resp = "welcome client".getBytes();
            // ByteBuf respBuf = Unpooled.buffer(resp.length);
            // respBuf.writeBytes(resp);
            // ctx.writeAndFlush(respBuf);

            // protocol解码
            MessagePayload payload = (MessagePayload) msg;
            logger.info("服务端收到消息：{}", payload.getContent());

            // 响应客户端（protocol编码）
            MessagePayload messagePayload = MessagePayload.newBuilder()
                    .setId(1)
                    .setContent("welcome clent")
                    .setType(2)
                    .setTime(LocalDateTime.now().toString())
                    .build();
            ctx.writeAndFlush(messagePayload);
        } finally {
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }
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
