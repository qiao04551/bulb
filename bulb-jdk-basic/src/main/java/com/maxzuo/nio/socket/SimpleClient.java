package com.maxzuo.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO网络通信-客户端
 * <p>
 * Created by zfh on 2019/12/01
 */
public class SimpleClient {

    public static void main(String[] args) throws IOException {
        // 建立连接（非阻塞模式）
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8090));
        socketChannel.configureBlocking(false);

        // 发送数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello world".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();

        socketChannel.close();
    }
}
