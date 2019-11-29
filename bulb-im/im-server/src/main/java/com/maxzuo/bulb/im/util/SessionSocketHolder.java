package com.maxzuo.bulb.im.util;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录客户端ID 映射 NioSocketChannel
 * <p>
 * Created by zfh on 2019/09/22
 */
public class SessionSocketHolder {

    private static final Map<String, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);

    private static final HashSet<String> SESSION_SET = new HashSet<>(16);

    /**
     * 保存客户端注册session
     */
    private static void saveSession(String username) {
        SESSION_SET.add(username);
    }

    /**
     * 移除客户端注册session
     */
    private static void removeSession(String username) {
        SESSION_SET.remove(username);
    }

    /**
     * 保存连接
     */
    private static void put(String username, NioSocketChannel channel) {
        CHANNEL_MAP.put(username, channel);
    }

    /**
     * 移除连接
     */
    private static void remove(String username) {
        CHANNEL_MAP.remove(username);
    }

    /**
     * 获取客户端信息
     */
    public static String getUserInfo (NioSocketChannel channel) {
        Set<Map.Entry<String, NioSocketChannel>> entries = CHANNEL_MAP.entrySet();
        for (Map.Entry<String, NioSocketChannel> entry : entries) {
            if (entry.getValue() == channel && SESSION_SET.contains(entry.getKey())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 获取用户映射的连接
     */
    public static NioSocketChannel getChannel (String username) {
        if (SESSION_SET.contains(username)) {
            Set<Map.Entry<String, NioSocketChannel>> entries = CHANNEL_MAP.entrySet();
            for (Map.Entry<String, NioSocketChannel> entry : entries) {
                if (entry.getKey().equals(username)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 客户端上线
     */
    public static void userOnline (String username, NioSocketChannel channel) {
        put(username, channel);
        saveSession(username);
    }

    /**
     * 客户端离线处理
     */
    public static void userOffLine (NioSocketChannel channel) {
        String username = null;
        Set<Map.Entry<String, NioSocketChannel>> entries = CHANNEL_MAP.entrySet();
        for (Map.Entry<String, NioSocketChannel> entry : entries) {
            if (entry.getValue() == channel) {
                username = entry.getKey();
            }
        }
        removeSession(username);
        remove(username);
    }
}
