package com.maxzuo.bulb.im.server;

import com.maxzuo.bulb.im.handler.BIMServerHandler;
import com.maxzuo.bulb.im.service.IServerRegisterService;
import com.maxzuo.bulb.im.util.OSUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * Netty-服务端
 * <p>
 * Created by zfh on 2019/09/22
 */
@Component
public class BIMServer {

    @Value("${server.port}")
    private Integer serverPort;

    @Autowired
    private IServerRegisterService serverRegisterService;

    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup worker = new NioEventLoopGroup();

    @PostConstruct
    public void start () {
        try {
            ServerBootstrap server = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline()
                                    // 当服务端所有ChannelHandler中4s内没有read()事件，则会触发userEventTriggered方法
                                    .addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new BIMServerHandler());
                        }
                    });

            // 绑定端口，开始接收进来的连接
            ChannelFuture future = server.bind(serverPort).sync();
            if (future.isSuccess()) {
                onlineService();
                System.out.println("Server start listen at " + serverPort);
            }

            /// 一直等待，直到socket关闭 （TODO: 会阻塞Spring容器！！！）
            // future.channel().closeFuture().sync();
        } catch (Exception e) {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    @PreDestroy
    public void destory () {
        worker.shutdownGracefully();
        boss.shutdownGracefully();
    }

    /**
     * 服务上线，注册zk
     */
    private void onlineService () {
        String localhostAddr = OSUtil.getLocalhostAddress();
        serverRegisterService.serverOnlineRegiste(localhostAddr, serverPort);
    }
}
