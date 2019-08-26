package com.maxzuo.redisson;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Redisson 常用方法（单Redis节点模式）
 * <p>
 * Created by zfh on 2019/08/26
 */
public class RedissonCommonMethod {

    private RedissonClient client;

    @Before
    public void init () {
        Config config = new Config();
        config.useSingleServer()
                .setDatabase(1)
                .setAddress("redis://192.168.3.183:6379")
                .setPassword("myredis");
        this.client = Redisson.create(config);
    }

    /**
     * 分布式整长形 RAtomicLong 对象
     */
    @Test
    public void testRAtomicLong () {
        RAtomicLong myLong = client.getAtomicLong("myLong");
        // 同步方式
        boolean b = myLong.compareAndSet(2, 3);
        System.out.println(b);

        // 异步执行方式
        RFuture<Boolean> result = myLong.compareAndSetAsync(3, 4);
        try {
            Boolean aBoolean = result.get();
            System.out.println(aBoolean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分布式集合
     */
    @Test
    public void testRMap () {
        RMap<String, String> myMap = client.getMap("myMap");
        myMap.put("name", "dazuo");
        System.out.println(myMap.get("name"));

        // 字段锁
        String key = "name";
        RLock keyLock = myMap.getLock(key);
        try {
            String s = myMap.get(key);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            keyLock.unlock();
        }
    }

    /**
     * 分布式锁
     */
    @Test
    public void testLock () {
        // 1.可重入锁（Reentrant Lock）
        RLock anyLock = client.getLock("anyLock");
        // 加锁时间，到期自动解锁
        anyLock.lock(10, TimeUnit.SECONDS);
        // 手动解锁
        anyLock.unlock();

        // 2.公平锁
        RLock fairLock = client.getFairLock("anyLock");

        // 3.联锁
        RLock lock1 = client.getLock("lock1");
        RLock lock2 = client.getLock("lock2");
        RLock lock3 = client.getLock("lock3");

        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        // 同时加锁：lock1 lock2 lock3
        // 所有的锁都上锁成功才算成功。
        lock.lock();
        lock.unlock();

        // 4.红锁 RedissonRedLock

        // 5.读写锁 RReadWriteLock

        // 6.信号量 RSemaphore

        // 7.闭锁 RCountDownLatch
    }
}
