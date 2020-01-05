package com.maxzuo.netty.mpush;

import com.maxzuo.netty.mpush.codec.Packet;
import com.maxzuo.netty.mpush.codec.PacketDecoder;
import com.maxzuo.netty.mpush.codec.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端
 * <p>
 * Created by zfh on 2020/01/05
 */
public class SimpleTCPServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTCPServer.class);

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new PacketDecoder())
                                    .addLast(new PacketEncoder())
                                    .addLast(new SimpleChannelInboundHandler<Packet>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
                                            // 心跳响应
                                            if (msg.getType() == Packet.HB_PACKET) {
                                                Packet packet = new Packet();
                                                packet.setType(Packet.HB_PACKET);
                                                ctx.writeAndFlush(packet);
                                            }
                                            logger.info("服务端收到消息：{}", msg);
                                        }
                                    });
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true);

            int port = 9999;
            ChannelFuture f = b.bind(port).addListener(future -> {
                if (future.isSuccess()) {
                    logger.info("server start success on {}", port);
                } else {
                    logger.info("server start failure on {}", port);
                }
            }).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
