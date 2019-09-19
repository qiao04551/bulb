package com.maxzuo.jedis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JedisCluster模块，对应Redis Cluster（集群模式）
 */
public class RedisCluster {

    private static JedisCluster jedisCluster = null;

    /**
     * 集群模式（单例模式）
     */
    public static JedisCluster getJedisCluster() {
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

        Set<HostAndPort> nodes = new HashSet<>();
        HostAndPort var1 = new HostAndPort("47.98.199.80", 6373);
        HostAndPort var2 = new HostAndPort("47.98.199.80", 6374);
        HostAndPort var3 = new HostAndPort("47.98.199.80", 6375);
        HostAndPort var4 = new HostAndPort("47.98.199.80", 6376);
        HostAndPort var5 = new HostAndPort("47.98.199.80", 6377);
        HostAndPort var6 = new HostAndPort("47.98.199.80", 6378);
        nodes.add(var1);
        nodes.add(var2);
        nodes.add(var3);
        nodes.add(var4);
        nodes.add(var5);
        nodes.add(var6);

        if (jedisCluster == null) {
            jedisCluster = new JedisCluster(nodes, config);
        }
        return jedisCluster;
    }

    private static final String EXPIRED_EVENT_CHANNEL = "__keyevent@0__:expired";

    /**
     * 连接集群
     */
    public static void main(String[] args) {
        jedisCluster = getJedisCluster();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();

        // 单个节点可以监听到
        JedisPool pool = clusterNodes.get("47.98.199.80:6374");
        Jedis jedis = pool.getResource();
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("channel: " + channel);
                System.out.println("message: " + message);
            }
        }, EXPIRED_EVENT_CHANNEL);
    }
}
