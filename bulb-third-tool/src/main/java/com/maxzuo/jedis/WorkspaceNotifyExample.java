package com.maxzuo.jedis;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 键空间通知 (需要配置服务器 键空间通知）
 * <p>
 * Created by zfh on 2018/10/15
 */
public class WorkspaceNotifyExample {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceNotifyExample.class);

    private static final String EXPIRED_EVENT_CHANNEL = "__keyevent@0__:expired";

    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis("47.98.199.80", 6379);
        jedis.auth("myredis");
    }

    /**
     * 向特定频道发布消息
     */
    @Test
    public void sendMessageToChannel() {
        String channel = "test_channel";
        String message = "hello channel";
        try {
            jedis.publish(channel, message);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            logger.info("Jedis publish channel={} message = {}", channel, message, e);
        } finally {
            jedis.close();
        }
    }

    /**
     * 订阅消息
     */
    @Test
    public void testSubscribeMessage() {
        System.out.println("SubscribeMain start ...");
        try {
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    // 过期键通知
                    if (EXPIRED_EVENT_CHANNEL.equals(channel)) {
                        System.out.println("key: " + message);
                    }
                    logger.info("收到来自 {} 的 message = {}", channel, message);
                }
            }, EXPIRED_EVENT_CHANNEL);

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            System.out.println("订阅异常：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
