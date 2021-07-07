package com.maxzuo.client;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.codec.BIMDecoder;
import com.maxzuo.codec.BIMEncoder;
import com.maxzuo.constant.Const;
import com.maxzuo.model.ChatMessageDTO;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * BIM 客户端
 * <p>
 * Created by zfh on 2019/09/22
 */
public class BIMClient {

    private static final Logger logger = LoggerFactory.getLogger(BIMClient.class);

    private static final String SERVER_IP = "127.0.0.1";

    private static final Integer SERVER_PORT = 8099;

    private void start () {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS))
                                    // .addLast("decoder", new StringDecoder())
                                    // .addLast("encoder", new StringEncoder())
                                    // 私有协议-编解码
                                    .addLast("decoder", new BIMDecoder())
                                    .addLast("encoder", new BIMEncoder())
                                    .addLast(new MessageHandler());
                        }
                    });
            ChannelFuture future = b.connect(SERVER_IP, SERVER_PORT).sync();
            sendStringMessage(future.channel());
            // 当通道关闭了就继续往下走
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.info("客户端连接异常！", e);
            group.shutdownGracefully();
        }
    }

    /**
     * 发送消息
     */
    public void sendStringMessage (Channel channel) {
        // 组织消息
        ChatMessageDTO chatmsg = new ChatMessageDTO();
        chatmsg.setCommandType(Const.CommandType.MSG);
        chatmsg.setFrom("mars");
        chatmsg.setMsg("hello gina");
        chatmsg.setTo("gina");
        byte[] content = JSONObject.toJSONBytes(chatmsg);
        // 写入通道
        ByteBuf message = Unpooled.buffer(content.length);
        message.writeBytes(content);
        ChannelFuture future = channel.writeAndFlush(message);
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                logger.debug("客户端发消息成功");
            }
        });
    }

    public static void main(String[] args) {
        BIMClient client = new BIMClient();
        client.start();
    }
}
