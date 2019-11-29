package com.maxzuo.bulb.im.client;

import com.maxzuo.bulb.im.common.ServerOnlineInfo;
import com.maxzuo.bulb.im.handler.BIMClientHandler;
import com.maxzuo.bulb.im.service.IServerDiscoveryService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * BIM 客户端
 * <p>
 * Created by zfh on 2019/09/22
 */
@Component
public class BIMClient {

    private static final Logger logger = LoggerFactory.getLogger(BIMClient.class);

    private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("bim-work"));

    @Value("${reconect.count}")
    private Integer maxReconnectCount;

    private Integer reconnectCount;

    @Autowired
    private IServerDiscoveryService serverDiscoveryService;

    private SocketChannel channel;

    @PostConstruct
    public void start() {
        ServerOnlineInfo serverOnlineInfo = serverDiscoveryService.obtainServerInfo();
        startClient(serverOnlineInfo);
    }

    @PreDestroy
    public void destory () {
        group.shutdownGracefully();
    }

    /**
     * 启动客户端
     */
    private void startClient (ServerOnlineInfo serverInfo) {
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new BIMClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(serverInfo.getIp(), serverInfo.getPort()).sync();
            channel = (SocketChannel) future.channel();
            reconnectCount = 0;

        } catch (Exception e) {
            logger.info("客户端连接异常！", e);
            reconnectCount++;
            if (reconnectCount >= maxReconnectCount) {
                throw new RuntimeException("达到最大重连次数！");
            }
        }
    }

    /**
     * 发送消息
     */
    public void sendStringMessage (byte[] content) {
        ByteBuf message = Unpooled.buffer(content.length);
        message.writeBytes(content);
        ChannelFuture future = channel.writeAndFlush(message);
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                logger.debug("客户端发消息成功");
            }
        });
    }

    /**
     * 客户端重连
     */
    public boolean reconnect() {
        if (channel != null && channel.isActive()) {
            return true;
        }
        logger.info("客户端开始重连！");
        start();
        return false;
    }
}
