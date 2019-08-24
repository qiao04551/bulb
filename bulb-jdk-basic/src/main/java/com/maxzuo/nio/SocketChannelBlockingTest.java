package com.maxzuo.nio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * NIO-基于通道网络通信（阻塞）
 * Created by zfh on 2019/01/24
 */
@DisplayName("基于TCP的socket通信（阻塞）")
class SocketChannelBlockingTest {

    @DisplayName("服务端")
    @Test
    void testServer() throws IOException {
        // 1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("spring2.png"), StandardOpenOption.WRITE,
            StandardOpenOption.CREATE);

        // 2.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(8090));

        // 3.获取客户端连接通道(阻塞)
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 4.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 5.读取客户端的数据（SocketChannel），保存到本地（FileChannel）
        while (socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        // 6.关闭通道
        socketChannel.close();
        outChannel.close();
        serverSocketChannel.close();
    }

    @DisplayName("客户端")
    @Test
    void testClient() throws IOException {
        // 1.获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8090));

        FileChannel inChannel = FileChannel.open(Paths.get("spring.png"), StandardOpenOption.READ);

        // 2.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 3.读取本地文件（FileChannel），并发送到服务端（SocketChannel）
        while (inChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        // 4.关闭通道
        inChannel.close();
        socketChannel.close();
    }
}
