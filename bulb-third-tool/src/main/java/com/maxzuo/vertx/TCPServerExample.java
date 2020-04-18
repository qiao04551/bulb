package com.maxzuo.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

/**
 * 创建TCP服务器
 * <p>
 * Created by zfh on 2019/07/25
 */
public class TCPServerExample {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        NetServer netServer = vertx.createNetServer();

        netServer.connectHandler(new Handler<NetSocket>() {
            @Override
            public void handle(NetSocket socket) {
                socket.handler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("接收到的数据：" + buffer.toString());

                        // 响应客户端
                        socket.write(Buffer.buffer("Server Received"));
                    }
                });

                // 监听客户端退出连接
                socket.closeHandler(new Handler<Void>() {
                    @Override
                    public void handle(Void event) {
                        System.out.println("客户端退出连接！");
                    }
                });
            }
        });

        netServer.listen(8888, new Handler<AsyncResult<NetServer>>() {
            @Override
            public void handle(AsyncResult<NetServer> event) {
                if (event.succeeded()) {
                    System.out.println("服务器启动成功，运行在8888端口");
                }
            }
        });
    }
}
