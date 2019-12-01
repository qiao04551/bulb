package com.maxzuo.im.nio.server;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.im.nio.common.entity.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 基于NIO的Server
 * <p>
 * Created by zfh on 2019/11/30
 */
public class BIMServer {

    private static final Logger logger = LoggerFactory.getLogger(BIMServer.class);

    private Map<String, SocketChannel> socketMap = new HashMap<>();

    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    private Charset charset = Charset.forName("UTF-8");

    private Selector selector;

    BIMServer(int port) {
        init(port);
    }

    private void init(int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            logger.error("【BIM聊天室-服务端】初始化异常！", e);
        }
    }

    /**
     * 服务器端轮询监听
     */
    void listen() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey sk = it.next();
                    handle(sk);
                    it.remove();
                }
            } catch (Exception e) {
                logger.error("【BIM聊天室-服务端】监听发生异常！", e);
            }
        }
    }

    /**
     * 处理事件
     */
    private void handle(SelectionKey sk) {
        try {
            if (sk.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) sk.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);

                String clientName = getClientName(socketChannel);
                socketMap.put(getClientName(socketChannel), socketChannel);
                logger.info("【BIM聊天室-服务端】新用户上线 clientName = {}", clientName);
            }
            if (sk.isReadable()) {
                SocketChannel channel = (SocketChannel) sk.channel();

                byteBuffer.clear();
                int count = channel.read(byteBuffer);
                if (count > 0) {
                    String receiveText = new String(byteBuffer.array(), 0, count);
                    sendMsgToUser(receiveText);
                }
                if (count == -1) {
                    channel.close();
                    String clientName = getClientName(channel);
                    socketMap.remove(clientName);
                    logger.info("【BIM聊天室-服务端】用户 {} 离线", clientName);
                }
            }
        } catch (Exception e) {
            logger.error("【BIM聊天室-服务端】处理事件发生异常！", e);
        }
    }

    /**
     * 生成客户端名称
     */
    private String getClientName (SocketChannel client) {
        Socket socket = client.socket();
        return "[" + socket.getInetAddress().toString().substring(1) + ":" + Integer.toHexString(client.hashCode()) + "]";
    }

    /**
     * 点对点发送消息
     */
    private void sendMsgToUser (String receiveText) {
        ChatMessageDTO chatMessageDTO = JSONObject.parseObject(receiveText, ChatMessageDTO.class);
        SocketChannel socketChannel = socketMap.get(chatMessageDTO.getTo());
        if (socketChannel != null) {
            byteBuffer.clear();
            byteBuffer.put(charset.encode(receiveText));
            byteBuffer.flip();
            try {
                socketChannel.write(byteBuffer);
            } catch (Exception e) {
                logger.error("【BIM聊天室-服务端】发送消息异常！", e);
            }
        } else {
            logger.warn("【BIM聊天室-服务端】用户 {} 不在线", chatMessageDTO.getTo());
        }
    }
}
