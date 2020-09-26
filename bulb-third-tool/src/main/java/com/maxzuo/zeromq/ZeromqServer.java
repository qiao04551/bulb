package com.maxzuo.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.nio.charset.StandardCharsets;

/**
 * 服务端 SocketType.REP
 * <p>
 * Created by zfh on 2020/09/26
 */
public class ZeromqServer {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://127.0.0.1:5555");

            while (!Thread.currentThread().isInterrupted()) {
                // 阻塞直到收到消息
                byte[] reply = socket.recv(0);
                System.out.println("Receive: [" + new String(reply, StandardCharsets.UTF_8) + "]");

                String response = "hello world";
                socket.send(response.getBytes(StandardCharsets.UTF_8), 0);
            }
        }
    }
}
