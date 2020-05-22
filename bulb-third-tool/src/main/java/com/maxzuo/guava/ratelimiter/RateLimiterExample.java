package com.maxzuo.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Guava RateLimiter类
 * <p>常用的限流算法有漏桶算法和令牌桶算法,guava的RateLimiter使用的是令牌桶算法,也就是以固定的频率向桶中放入令牌。
 *
 * <p>RateLimiter 从概念上来讲，速率限制器会在可配置的速率下分配许可证。如果必要的话，每个acquire() 会阻塞当前线程直到许可证可用后
 * 获取该许可证。一旦获取到许可证，不需要再释放许可证。
 *
 * <p>RateLimiter经常用于限制对一些物理资源或者逻辑资源的访问速率。与Semaphore 相比，Semaphore 限制了并发访问的数量而不是使用速率。
 *
 * <p>参考文章：
 * 限流算法之漏桶算法、令牌桶算法：https://www.cnblogs.com/SUNSHINEC/p/9577682.html
 * 高并发系统之限流特技：https://www.iteye.com/blog/jinnianshilongnian-2305117
 *
 * Created by zfh on 2020/04/28
 */
public class RateLimiterExample {

    private static ExecutorService pool = Executors.newCachedThreadPool();

    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        // 配置速率：每2秒1次
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        System.out.println(rateLimiter.getRate());

        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                if (rateLimiter.tryAcquire(5, TimeUnit.SECONDS)) {
                    System.out.println("index: " + count.incrementAndGet() + ", time = " + new Date());
                }
            });
        }
    }
}
