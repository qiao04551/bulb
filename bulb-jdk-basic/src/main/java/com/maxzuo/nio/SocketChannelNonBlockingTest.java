package com.maxzuo.nio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO-基于通道的网络通信（非阻塞）
 * Created by zfh on 2019/01/24
 */
class SocketChannelNonBlockingTest {

    @DisplayName("服务端")
    @Test
    void testServer() throws IOException {
        // 1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 2.切换为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        // 3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(8090));

        // 4.获取选择器
        Selector selector = Selector.open();

        // 5.将通道注册到选择器上，并且指定“监听接受时间”
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 6.轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select() > 0) {
            // 7.获取当前选择器上所有注册的“选择键”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                // 8.执行“已就绪”的监听事件
                if (sk.isAcceptable()) {
                    // 10.若“接收就绪”，则获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 11.切换为非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 12.将该通道注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                // 13.执行“已可读”的监听事件
                if (sk.isReadable()) {
                    // 14.获取可读的通道
                    SocketChannel channel = (SocketChannel) sk.channel();
                    // 15.读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int byteCount;
                    while ((byteCount = channel.read(byteBuffer)) != -1) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, byteCount));
                        byteBuffer.clear();
                    }
                }
                // 16.移除已处理的选择键
                it.remove();
            }
        }
    }

    @DisplayName("客户端")
    @Test
    void testClient() throws IOException {
        // 1.获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8090));
        // 2.切换为非阻塞模式
        socketChannel.configureBlocking(false);
        // 3.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 4.发送数据给服务端
        String msg = "hello future";
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
        // 5.关闭通道
        socketChannel.close();
    }
}
