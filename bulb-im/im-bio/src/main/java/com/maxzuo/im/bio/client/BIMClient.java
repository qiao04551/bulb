package com.maxzuo.im.bio.client;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.im.bio.common.entity.ChatMessageDTO;
import com.maxzuo.im.bio.common.entity.Message;
import com.maxzuo.im.bio.common.protocol.BIMChatRoomProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.maxzuo.im.bio.common.constant.Const.CommandType;

/**
 * 基于BIO阻塞模型的客户端
 * <p>
 * Created by zfh on 2019/11/30
 */
public class BIMClient implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BIMClient.class);

    private String host;

    private int port;

    private Socket socket;

    private ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);

    public BIMClient (String host, int port) {
        this.host = host;
        this.port = port;

        // 心跳检测
        scheduledPool.scheduleAtFixedRate(() -> {
            Message message = new Message();
            message.setCommandType(CommandType.PING);
            message.setPayLoad("PING");
            try {
                sendMsgToUser(message);
            } catch (Exception e) {
                logger.info("【BIM聊天室-客户端】发送心跳异常！");
            }
        }, 5, 10, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            while (true) {
                InputStream inputStream = socket.getInputStream();
                Message message = BIMChatRoomProtocol.parse(inputStream);
                switch (message.getCommandType()) {
                    case CommandType.MSG:
                        ChatMessageDTO chatDTO = JSONObject.parseObject(message.getPayLoad(), ChatMessageDTO.class);
                        logger.info("【BIM聊天室-客户端】收到 {} 的消息：{}", chatDTO.getFrom(), message.getPayLoad());
                        break;
                    case CommandType.PING:
                        logger.debug("【BIM聊天室-客户端】收到服务端心跳响应 payLoad = {}", message.getPayLoad());
                        break;
                    default:
                        logger.warn("【BIM聊天室-客户端】无法处理的消息类型！");
                }
            }
        } catch (Exception e) {
            logger.error("【BIM聊天室-客户端】初始化连接异常！", e);
        }
    }

    /**
     * 启动客户端
     */
    void start() {
        new Thread(this).start();
    }

    /**
     * 发送消息给指定用户
     * @param message {@link Message}
     */
    public void sendMsgToUser (Message message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        BIMChatRoomProtocol.write(outputStream, message);
    }
}
