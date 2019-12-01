package com.maxzuo.im.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于NIO的聊天室服务端
 * <p>
 * Created by zfh on 2019/11/30
 */
public class ServerBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(ServerBootstrap.class);

    public static void main(String[] args) {
        BIMServer bimServer = new BIMServer(8888);
        logger.info("BIMServer run at 127.0.0.1:8888");
        bimServer.listen();
    }
}
