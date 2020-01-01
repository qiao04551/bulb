package com.maxzuo.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Websocket服务器
 * <pre>
 *   客户端连接地址：ws://localhost:8081/websocket
 * </pre>
 *
 * Created by zfh on 2020/01/01
 */
public class NettyWebsocketServer {

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
                                    // 将请求和应答消息编码或者解码为HTTP消息
                                    .addLast("http-codec",new HttpServerCodec())
                                    // 将HTTP消息的多个部分组合成一条完整的HTTP消息
                                    .addLast("aggregator", new HttpObjectAggregator(65536))
                                    // 用于大数据的分区传输
                                    .addLast("http-chunked", new ChunkedWriteHandler())
                                    .addLast("handler", new NioWebSocketHandler());
                        }
                    });

            Channel channel = b.bind(8081).sync().channel();
            System.out.println("Websocket server started at port " + 9999);
            channel.closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
