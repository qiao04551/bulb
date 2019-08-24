package com.maxzuo.nio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 选择器的使用
 * Created by zfh on 2019/01/23
 */
class SelectorTest {

    @DisplayName("创建选择器")
    @Test
    void testCreateSelector() throws IOException {
        Selector selector = Selector.open();
    }

    @DisplayName("向选择器注册通道")
    @Test
    void testToSelectorRegisterChannel() throws IOException {
        // 创建一个套接字
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9998);

        // 获取socketChannel
        SocketChannel channel = socket.getChannel();

        // 创建选择器
        Selector selector = Selector.open();

        // 将socketChannel切换为非阻塞模式
        channel.configureBlocking(false);

        // 向selector注册通道
        channel.register(selector, SelectionKey.OP_WRITE);

        // 监听多个事件，通过“位或”操作符连接
        //int interestSet = SelectionKey.OP_ACCEPT|SelectionKey.OP_READ;
        //channel.register(selector, interestSet);
    }
}
