package com.maxzuo.im.bio.client;

import com.maxzuo.im.bio.client.scanner.Scan;

/**
 * 聊天室-客户端
 * <p>
 * Created by zfh on 2019/11/30
 */
public class ClientBootstrap {

    public static void main(String[] args) {
        BIMClient bimClient = new BIMClient("127.0.0.1", 8888);
        bimClient.start();

        new Scan(bimClient).start();
    }
}
