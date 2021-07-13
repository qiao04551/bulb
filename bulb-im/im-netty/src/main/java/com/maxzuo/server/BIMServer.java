package com.maxzuo.server;

import com.maxzuo.codec.BIMDecoder;
import com.maxzuo.codec.BIMEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Netty-服务端
 * <p>
 * Created by zfh on 2019/09/22
 */
public class BIMServer {

    private static final Integer serverPort = 8099;

    public void start () {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    // .childOption(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline()
                                    // 每10秒会进行一次读检测，当10s内没有read事件，则会触发userEventTriggered方法
                                    .addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS))
                                    // .addLast("decoder", new StringDecoder())
                                    // .addLast("encoder", new StringEncoder())
                                    // 私有协议-编解码
                                    .addLast("decoder", new BIMDecoder())
                                    .addLast("encoder", new BIMEncoder())
                                    .addLast(new MessageHandler());
                        }
                    });
            ChannelFuture future = b.bind(serverPort).sync();
            if (future.isSuccess()) {
                System.out.println("Server start listen at " + serverPort);
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
