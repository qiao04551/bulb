package com.maxzuo.netty.hello;

import com.maxzuo.proto.protocol.MessagePayload;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Netty客户端
 * <p>
 * Created by zfh on 2020/01/01
 */
public class HelloClient {

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
                                        .addLast(new ProtobufVarint32FrameDecoder())
                                        .addLast(new ProtobufDecoder(MessagePayload.getDefaultInstance()))
                                        .addLast(new ProtobufVarint32LengthFieldPrepender())
                                        .addLast(new ProtobufEncoder())
                                        .addLast(new TimeClientHandler());
                        }
                    });
            // 发起异步连接请求
            ChannelFuture f = b.connect("127.0.0.1", 9999).sync();

            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
}
