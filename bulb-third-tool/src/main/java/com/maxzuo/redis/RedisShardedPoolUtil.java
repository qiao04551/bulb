package com.maxzuo.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * ShardedJedis模块，对应Redis Sharding（分片模式）
 */
public class RedisShardedPoolUtil {

    private static final String     HOST_1    = "47.98.199.80";

    private static final Integer    PORT_1    = 6377;

    private static final Integer    TIMEOUT_1 = 2000;

    private static final String     HOST_2    = "47.98.199.80";

    private static final Integer    PORT_2    = 6378;

    private static final Integer    TIMEOUT_2 = 2000;

    private static final String     PASSWORD  = "dazuo.123";

    private static final Integer    DATABASE  = 0;

    private static ShardedJedisPool pool;

    static {
        init();
    }

    /**
     * 初始化连接池（Shreded分片连接池）
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
        config.setTestOnReturn(false);
        // 连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        config.setBlockWhenExhausted(true);

        JedisShardInfo var1 = new JedisShardInfo(HOST_1, PORT_1, TIMEOUT_1);
        var1.setPassword("dazuo.123");
        JedisShardInfo var2 = new JedisShardInfo(HOST_2, PORT_2, TIMEOUT_2);
        var2.setPassword("dazuo.123");
        List<JedisShardInfo> var3 = new ArrayList<>();
        var3.add(var1);
        var3.add(var2);
        // Todo: 参数三表示hash一致算法；参数四：待考究
        pool = new ShardedJedisPool(config, var3, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    /**
     * 分片（hash一致算法）
     * @return ShardedJedis
     */
    public static ShardedJedis getShardedJedis() {
        return pool.getResource();
    }

    public static void main(String[] args) {
        ShardedJedis shardedJedis = getShardedJedis();
        shardedJedis.set("name", "dazuo");
        shardedJedis.close();
    }
}
