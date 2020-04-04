package com.maxzuo.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Jedis 对象池（Redis Standalone 单节点模式）
 * <p>
 * Created by zfh on 2018/10/15
 */
public class JedisPoolExample {

    private static final String HOST = "192.168.1.1";

    private static final Integer PORT = 6379;

    private static final Integer TIMEOUT = 2000;

    private static final String PASSWORD = "myredis";

    private static final Integer DATABASE = 1;

    private static JedisPool pool;

    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大活动对象数
        config.setMaxTotal(6);
        // 最大能够保持idel状态的对象数
        config.setMaxIdle(2);
        // 最小能够保持idel状态的对象数
        config.setMinIdle(2);
        // 从jedis连接池获取连接时，是否进行验证操作。如果赋值true，则得到的jedis实例肯定是可用的。
        config.setTestOnBorrow(true);
        // 把连接放回jedis连接池时，是否进行验证操作。如果赋值为true，则放回jedisPool的jedis实例肯定是可用的。
        config.setTestOnReturn(true);
        // 连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        config.setBlockWhenExhausted(false);
        pool = new JedisPool(config, HOST, PORT, TIMEOUT, PASSWORD, DATABASE);
    }

    public static void main(String[] args) {
        try {
            int c = 0;
            while (++c < 20) {
                Thread.sleep(1000);
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("begin ...");
                        Jedis jedis = pool.getResource();
                        System.out.println(jedis);
                        try {
                            Thread.sleep(Integer.MAX_VALUE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // jedis.close();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
