package com.maxzuo.im.nio.client;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.im.nio.common.entity.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 基于NIO聊天室-客户端
 * Created by zfh on 2019/11/30
 */
public class BIMClient implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BIMClient.class);

    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    private Charset charset = Charset.forName("UTF-8");

    private SocketChannel socketChannel;

    private String host;

    private int port;

    BIMClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            // 将套接字通道置于非阻塞模式下，然后调用其connect方法，从而启动非阻塞连接操作。
            // 一旦建立了连接，或者尝试失败，套接字通道将变为可连接的，可以调用此方法来完成
            // 连接序列。如果连接操作失败，则调用此方法将引发适当的IOException。
            socketChannel.connect(new InetSocketAddress(host, port));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey sk = it.next();
                    if (sk.isConnectable()) {
                        SocketChannel channel = (SocketChannel) sk.channel();
                        if (channel.finishConnect()) {
                            logger.info("【BIM聊天室-客户端】连接成功！");
                            sk.interestOps(SelectionKey.OP_READ);
                        } else {
                            logger.error("【BIM聊天室-客户端】连接失败！");
                        }
                    }
                    if (sk.isReadable()) {
                        SocketChannel channel = (SocketChannel) sk.channel();

                        byteBuffer.clear();
                        int count = channel.read(byteBuffer);
                        if (count > 0) {
                            String receiveText = new String(byteBuffer.array(), 0, count);
                            logger.info("【BIM聊天室-客户端】收到消息 msg = {}", receiveText);
                        }
                    }
                    it.remove();
                }
            }
        } catch (Exception e) {
            logger.error("【BIM聊天室-客户端】初始化异常！", e);
        }
    }

    /**
     * 发送消息
     */
    public void sendMsgToUser (ChatMessageDTO messageDTO) {
        byteBuffer.clear();
        byteBuffer.put(charset.encode(JSONObject.toJSONString(messageDTO)));
        try {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (Exception e) {
            logger.error("【BIM聊天室-客户端】发送消息异常！", e);
        }
    }
}
