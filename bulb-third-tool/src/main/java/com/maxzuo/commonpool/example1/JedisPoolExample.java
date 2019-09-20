package com.maxzuo.commonpool.example1;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.NoSuchElementException;

/**
 * 自定义JedisPool
 * <p>
 * Created by zfh on 2019/09/20
 */
public class JedisPoolExample {

    private GenericObjectPool<Jedis> objectPool;

    private JedisPoolExample() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 最大活动对象数
        poolConfig.setMaxTotal(6);
        // 最大能够保持idel状态的对象数
        poolConfig.setMaxIdle(2);
        // 最小能够保持idel状态的对象数
        poolConfig.setMinIdle(2);
        // 从jedis连接池获取连接时，是否进行验证操作。如果赋值true，则得到的jedis实例肯定是可用的。
        poolConfig.setTestOnBorrow(true);
        // 把连接放回jedis连接池时，是否进行验证操作。如果赋值为true，则放回jedisPool的jedis实例肯定是可用的。
        poolConfig.setTestOnReturn(true);
        // 连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        poolConfig.setBlockWhenExhausted(false);

        objectPool = new GenericObjectPool<>(new JedisFactoryExample("47.98.199.80", 6379, "myredis"), poolConfig);
    }

    /**
     * 借用对象
     */
    private Jedis getResource() {
        try {
            return objectPool.borrowObject();
        } catch (NoSuchElementException nse) {
            throw new JedisException("Could not get a resource from the pool", nse);
        } catch (Exception e) {
            throw new JedisConnectionException("Could not get a resource from the pool", e);
        }
    }

    /**
     * 归还对象
     */
    private void returnResourceObject(Jedis resource) {
        if (resource == null) {
            return;
        }
        try {
            objectPool.returnObject(resource);
        } catch (Exception e) {
            throw new JedisException("Could not return the resource to the pool", e);
        }
    }

    public void destroy() {
        try {
            objectPool.close();
        } catch (Exception e) {
            throw new JedisException("Could not destroy the pool", e);
        }
    }

    public int getNumActive() {
        if (poolInactive()) {
            return -1;
        }
        return this.objectPool.getNumActive();
    }

    public int getNumIdle() {
        if (poolInactive()) {
            return -1;
        }
        return this.objectPool.getNumIdle();
    }

    public int getNumWaiters() {
        if (poolInactive()) {
            return -1;
        }
        return this.objectPool.getNumWaiters();
    }

    public long getMeanBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }
        return this.objectPool.getMeanBorrowWaitTimeMillis();
    }

    public long getMaxBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }
        return this.objectPool.getMaxBorrowWaitTimeMillis();
    }

    private boolean poolInactive() {
        return this.objectPool == null || this.objectPool.isClosed();
    }

    public void addObjects(int count) {
        try {
            for (int i = 0; i < count; i++) {
                this.objectPool.addObject();
            }
        } catch (Exception e) {
            throw new JedisException("Error trying to add idle objects", e);
        }
    }

    public static void main(String[] args) {
        JedisPoolExample jedisPoolExample = new JedisPoolExample();

        // 借用对象
        Jedis jedis = jedisPoolExample.getResource();
        String name = jedis.get("name");
        System.out.println(name);

        // 归还对象
        jedisPoolExample.returnResourceObject(jedis);
    }
}
