package com.maxzuo.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 本地线程间通信 inproc://xx
 * <p>
 * Created by marszuo on 2020/09/28
 */
public class InprocExample {

    public static void main(String[] args) {
        try(ZContext context = new ZContext()) {
            Thread receiveT1 = new Thread(() -> {
                ZMQ.Socket socket = context.createSocket(SocketType.PAIR);
                socket.bind("inproc://#1");

                while (!Thread.currentThread().isInterrupted()) {
                    // 阻塞直到收到消息
                    byte[] reply = socket.recv(0);
                    System.out.println("Receive: [" + new String(reply, StandardCharsets.UTF_8) + "]");

                    String response = "hello world";
                    socket.send(response.getBytes(StandardCharsets.UTF_8), 0);
                }
            });
            receiveT1.start();

            Thread publishT = new Thread(() -> {
                ZMQ.Socket socket = context.createSocket(SocketType.PAIR);
                socket.connect("inproc://#1");

                for (int n = 0; n < 5; n ++) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String message = "hello " + n;
                    socket.send(message.getBytes(StandardCharsets.UTF_8), 0);
                    byte[] reply = socket.recv(0);
                    System.out.println("Received:[" + new String(reply, StandardCharsets.UTF_8) + "]");
                }
            });
            publishT.start();
        }
    }
}
