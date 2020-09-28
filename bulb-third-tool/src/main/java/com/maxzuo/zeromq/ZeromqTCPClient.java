package com.maxzuo.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.nio.charset.StandardCharsets;

/**
 * TCP单播通信：客户端
 * <p>
 * Created by zfh on 2020/09/26
 */
public class ZeromqTCPClient {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://127.0.0.1:5555");

            socket.send("hello".getBytes(StandardCharsets.UTF_8), 0);

            byte[] reply = socket.recv(0);
            System.out.println("Received:[" + new String(reply, StandardCharsets.UTF_8) + "]");
        }
    }
}
