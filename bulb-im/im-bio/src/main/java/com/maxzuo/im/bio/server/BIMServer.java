package com.maxzuo.im.bio.server;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.im.bio.common.entity.ChatMessageDTO;
import com.maxzuo.im.bio.common.entity.LoginMessageDTO;
import com.maxzuo.im.bio.common.entity.Message;
import com.maxzuo.im.bio.common.protocol.BIMChatRoomProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.maxzuo.im.bio.common.constant.Const.CommandType;

/**
 * 基于BIO阻塞模型的服务端
 * <p>
 * Created by zfh on 2019/11/30
 */
public class BIMServer {

    private static final Logger logger = LoggerFactory.getLogger(BIMServer.class);

    /**
     * 连接池（维护客户端连接）
     */
    private final ExecutorService pool = Executors.newCachedThreadPool();

    /**
     * 用户和连接的映射
     */
    private final ConcurrentHashMap<String, Socket> chatMap = new ConcurrentHashMap<>(10);

    private ServerSocket serverSocket;

    BIMServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            logger.error("【BIM聊天室-服务端】初始化异常！", e);
        }
    }

    /**
     * 启动服务端，接受新连接
     */
    void start() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                logger.info("【BIM聊天室-服务端】新连接进入！");
                pool.execute(() -> handleClient(socket));
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * 客户端（分别独立线程）
     */
    private void handleClient(Socket socket) {
        while (true) {
            try {
                InputStream is = socket.getInputStream();
                Message message = BIMChatRoomProtocol.parse(is);
                switch (message.getCommandType()) {
                    case CommandType.LOGIN:
                        LoginMessageDTO loginDTO = JSONObject.parseObject(message.getPayLoad(), LoginMessageDTO.class);
                        chatMap.put(loginDTO.getUsername(), socket);
                        logger.info("【BIM聊天室-服务端】用户登录 username = {}", loginDTO.getUsername());
                        break;
                    case CommandType.MSG:
                        ChatMessageDTO chatDTO = JSONObject.parseObject(message.getPayLoad(), ChatMessageDTO.class);
                        // 转发消息
                        dispatchMsg(chatDTO);
                        break;
                    case CommandType.PING:
                        logger.info("【BIM聊天室-服务端】客户端心跳检测 payLoad = {}", message.getPayLoad());

                        // 服务端心跳响应
                        message.setPayLoad("PONG");
                        BIMChatRoomProtocol.write(socket.getOutputStream(), message);
                        break;
                    default:
                        logger.info("【BIM聊天室-服务端】不能识别的消息！");
                }
            } catch (Exception e) {
                logger.info("【BIM聊天室-服务端】客户端连接异常！");
                String username = getUsername(socket);
                if (username != null) {
                    chatMap.remove(username);
                    logger.info("【BIM聊天室-服务端】{} 用户下线！", username);
                }
                break;
            }
        }
    }

    /**
     * 发送给指定用户
     *
     * @param messageDTO {@link ChatMessageDTO}
     */
    private void dispatchMsg(ChatMessageDTO messageDTO) {
        Message message = new Message();
        message.setCommandType(CommandType.MSG);
        message.setPayLoad(JSONObject.toJSONString(messageDTO));
        sendMsg(messageDTO.getTo(), message);
    }

    /**
     * 统一发送消息
     * @param username 接收消息用户
     * @param message {@link Message}
     */
    private void sendMsg(String username, Message message) {
        try {
            Socket socket = chatMap.get(username);
            if (socket == null) {
                logger.warn("【BIM聊天室-服务端】用户 {} 不在线！", username);
                return;
            }
            OutputStream outputStream = socket.getOutputStream();
            BIMChatRoomProtocol.write(outputStream, message);
        } catch (Exception e) {
            logger.warn("【BIM聊天室-服务端】消息发送异常！");
        }
    }

    /**
     * 用户断线，移除用户
     * @param socket 连接
     * @return 连接映射的用户名
     */
    private String getUsername(Socket socket) {
        Set<Map.Entry<String, Socket>> entries = chatMap.entrySet();
        for (Map.Entry<String, Socket> entry : entries) {
            if (socket.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
