package com.maxzuo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Jedis操作工具类
 * Created by zfh on 2018/10/15
 */
public class RedisUtils {

    private static final Logger  logger = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * 过期时间
     */
    private static final Integer EXPIRE = 20 * 60 * 60;

    /**
     * 设置Value
     */
    public static void setStr(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            jedis.set(key, value);
            jedis.expire(key, EXPIRE);
        } catch (Exception e) {
            logger.info("Jedis set key={} value={}", key, value, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取Value
     */
    public static String getStr(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.info("Jedis get key={}", key, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 删除key
     */
    public static void delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            logger.info("Jedis del key={}", key, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 向特定频道发布消息
     */
    public static void sendMessageToChannel(String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtil.getJedis();
            jedis.publish(channel, message);
        } catch (Exception e) {
            logger.info("Jedis publish channel={} message = {}", channel, message, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
