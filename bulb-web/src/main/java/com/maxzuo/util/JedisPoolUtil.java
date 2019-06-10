package com.maxzuo.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis对应 Redis Standalone（单节点模式）
 * Created by zfh on 2018/10/15
 */
public class JedisPoolUtil {

    private static final String  HOST     = "192.168.3.183";

    private static final Integer PORT     = 6379;

    private static final Integer TIMEOUT  = 2000;

    private static final String  PASSWORD = "myredis";

    private static final Integer DATABASE = 1;

    /**
     * Redis连接池
     */
    private static JedisPool     pool;

    static {
        init();
    }

    /**
     * 初始化连接池
     */
    private static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大活动对象数
        config.setMaxTotal(100);
        // 最大能够保持idel状态的对象数
        config.setMaxIdle(20);
        // 最小能够保持idel状态的对象数
        config.setMinIdle(20);
        // 从jedis连接池获取连接时，是否进行验证操作。如果赋值true，则得到的jedis实例坑定是可用的。
        config.setTestOnBorrow(true);
        // 把连接放回jedis连接池时，是否进行验证操作。如果赋值为true，则放回jedisPool的jedis实例肯定是可用的。
        config.setTestOnReturn(true);
        // 连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, HOST, PORT, TIMEOUT, PASSWORD, DATABASE);
    }

    /**
     * 获取连接
     * @return Jedis
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }
}
