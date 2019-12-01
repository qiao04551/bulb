package com.maxzuo.im.nio.client;

import com.maxzuo.im.nio.client.scanner.Scan;

/**
 * 基于NIO的聊天室-客户端
 * <p>
 * Created by zfh on 2019/11/30
 */
public class ClientBootstrap {

    public static void main(String[] args) {
        BIMClient bimClient = new BIMClient("127.0.0.1", 888);
        bimClient.start();

        new Scan(bimClient).start();
    }
}
