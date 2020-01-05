package com.maxzuo.netty.marshalling.client;

import com.maxzuo.netty.marshalling.MarshallingCodecFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * <p>
 * Created by zfh on 2020/01/05
 */
public class SubReqClient {

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
                                    .addLast(MarshallingCodecFactory.buildMarshallingDecoder())
                                    .addLast(MarshallingCodecFactory.buildMarshallingEncoder())
                                    .addLast(new SubReqClientHandler());
                        }
                    });

            ChannelFuture f = b.connect("127.0.0.1", 9999).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
