package com.maxzuo.netty.mpush;

import com.maxzuo.netty.mpush.codec.Packet;
import com.maxzuo.netty.mpush.codec.PacketDecoder;
import com.maxzuo.netty.mpush.codec.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 客户端
 * <p>
 * Created by zfh on 2020/01/05
 */
public class SimpleTCPClient {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTCPClient.class);

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS))
                                    .addLast(new PacketDecoder())
                                    .addLast(new PacketEncoder())
                                    .addLast(new SimpleChannelInboundHandler<Packet>() {
                                        @Override
                                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                            if (evt instanceof IdleStateEvent) {
                                                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                                                if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                                                    // 发送心跳
                                                    Packet packet = new Packet();
                                                    packet.setType(Packet.HB_PACKET);
                                                    ctx.writeAndFlush(packet);
                                                }
                                            }
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            Packet message = new Packet();
                                            message.setType((byte) 2);
                                            message.setSessionId(123L);
                                            message.setBody("hello server".getBytes());
                                            ctx.writeAndFlush(message);
                                        }

                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
                                            logger.info("客户端收到消息：{}", msg);
                                        }
                                    });
                        }
                    });
            ChannelFuture f = b.connect("127.0.0.1", 9999).addListener(future -> {
                if (future.isSuccess()) {
                    logger.info("客户端连接成功");
                } else {
                    logger.info("客户端连接失败！");
                }
            }).sync();

            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
