package com.maxzuo.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

/**
 * 创建TCP客户端
 * <p>
 * Created by zfh on 2019/07/25
 */
public class TCPClientExample {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();

        netClient.connect(8888, "localhost", new Handler<AsyncResult<NetSocket>>() {
            @Override
            public void handle(AsyncResult<NetSocket> event) {
                if (event.succeeded()) {
                    NetSocket socket = event.result();

                    // 向服务端写数据
                    socket.write(Buffer.buffer("hello server"));

                    // 读取服务端响应的数据
                    socket.handler(new Handler<Buffer>() {
                        @Override
                        public void handle(Buffer buffer) {
                            System.out.println("客户端接收到的数据：" + buffer.toString());
                        }
                    });
                } else {
                    System.out.println("连接服务器异常！");
                }
            }
        });
    }
}
