package com.maxzuo.bulb.im.util;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录客户端ID 映射 NioSocketChannel
 * <p>
 * Created by zfh on 2019/09/22
 */
public class SessionSocketHolder {

    private static final Map<Integer, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);

    private static final Map<Integer, String> SESSION_MAP = new ConcurrentHashMap<>(16);

    /**
     * 保存客户端注册session
     */
    public static void saveSession (Integer userid, String username) {
        SESSION_MAP.put(userid, username);
    }

    /**
     * 移除客户端注册session
     */
    public static void removeSession (Integer userid) {
        SESSION_MAP.remove(userid);
    }

    /**
     * 保存连接
     */
    public static void put(Integer userid, NioSocketChannel channel) {
        CHANNEL_MAP.put(userid, channel);
    }

    /**
     * 移除连接
     */
    public static void remove (Integer userid) {
        CHANNEL_MAP.remove(userid);
    }

    /**
     * 获取客户端信息
     */
    public static String getUserInfo (NioSocketChannel channel) {
        Set<Map.Entry<Integer, NioSocketChannel>> entries = CHANNEL_MAP.entrySet();
        for (Map.Entry<Integer, NioSocketChannel> entry : entries) {
            if (entry.getValue() == channel) {
                Set<Map.Entry<Integer, String>> sessionEntres = SESSION_MAP.entrySet();
                for (Map.Entry<Integer, String> userEntry : sessionEntres) {
                    if (entry.getKey().equals(userEntry.getKey())) {
                        return userEntry.getValue();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 客户端上线
     */
    public static void userOnline (Integer userid, String username, NioSocketChannel channel) {
        put(userid, channel);
        saveSession(userid, username);
    }

    /**
     * 客户端离线处理
     */
    public static void userOffLine (NioSocketChannel channel) {
        Integer userid = null;
        Set<Map.Entry<Integer, NioSocketChannel>> entries = CHANNEL_MAP.entrySet();
        for (Map.Entry<Integer, NioSocketChannel> entry : entries) {
            if (entry.getValue() == channel) {
                userid = entry.getKey();
            }
        }
        removeSession(userid);
        remove(userid);
    }
}
