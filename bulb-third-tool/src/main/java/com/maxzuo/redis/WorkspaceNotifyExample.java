package com.maxzuo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 键空间通知
 * Created by zfh on 2018/10/15
 */
public class WorkspaceNotifyExample {

    private static final String EXPIRED_EVENT_CHANNEL = "__keyevent@0__:expired";

    public static void main(String[] args) {
        System.out.println("SubscribeMain start ...");
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    if (EXPIRED_EVENT_CHANNEL.equals(channel)) {
                        System.out.println("key: " + message);
                    }
                }
            }, EXPIRED_EVENT_CHANNEL);
        } catch (Exception e) {
            System.out.println("订阅异常：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
