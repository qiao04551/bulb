package com.maxzuo.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Guava缓存使用 + 异步缓存刷新
 * <p>
 * Created by zfh on 2019/12/05
 */
public class GuavaCacheExample {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private final LoadingCache<String, String> cache;

    public GuavaCacheExample() {
        cache = CacheBuilder.newBuilder().maximumSize(3)
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(@NotNull RemovalNotification<String, String> rn) {
                        System.out.println("remove key: " + rn.getKey());
                    }
                })
                .build(new CacheLoader<String, String>() {
                    @NotNull
                    @Override
                    public String load(@NotNull String key) throws Exception {
                        if (key.equals("email")) {
                            return "111@qq.com";
                        }
                        if (key.equals("name")) {
                            TimeUnit.SECONDS.sleep(3);
                            return "gina";
                        }
                        return "null-val";
                    }

                    @NotNull
                    @Override
                    public ListenableFuture<String> reload(@NotNull String key, @NotNull String oldValue) throws Exception {
                        long tid = Thread.currentThread().getId();
                        System.out.printf("reload cache key = %s, oldValue = %s, tid = %d\n", key, oldValue, tid);

                        // 默认的 reload 方法就是同步去执行 load 方法
                        // return super.reload(key, oldValue);

                        // 重写 reload 方法实现异步刷新缓存
                        ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
                            @Override
                            public String call() throws Exception {
                                return load(key);
                            }
                        });
                        threadPool.execute(task);
                        return task;
                    }
                });
    }

    private void putAndGet() {
        cache.put("name", "gina");
        cache.put("age", "25");
        cache.put("city", "wuhan");
        // 当超出最大容量时, 将尝试回收最近没有使用或使用很少的缓存项
        cache.put("email", "123@qq.com");
        cache.put("phone", "18800000001");
        // 手动丢弃数据
        cache.invalidate("email");

        // 当值不存在时，走 CacheLoader 重新生成
        String email = cache.getUnchecked("email");
        System.out.println("email: " + email);
    }

    /**
     * 获取过期的数据
     * <pre>
     *  缓存更新策略：
     *  - expireAfterWrite：缓存写入后多久过期。
     *  - expireAfterAccess 缓存读取后多久过期。
     *  - refreshAfterWrite 缓存写入后多久更新。
     *
     *  触发缓存更新的条件：
     *  - 缓存过期
     *  - get数据的时候
     * </pre>
     */
    private void getExpiredKeyAndRefreshCache() throws InterruptedException {
        cache.put("name", "mars");
        TimeUnit.SECONDS.sleep(3);

        System.out.println("begin get cache");
        String name = cache.getUnchecked("name");
        System.out.println("first cache value: " + name);

        // 等待异步缓存更新完成
        TimeUnit.SECONDS.sleep(3);
        System.out.println("retry get cache");
        name = cache.getUnchecked("name");
        System.out.println("second cache value: " + name);
    }

    /**
     * 并发获取过期的数据
     * <pre>
     *   refreshAfterWrite 是允许一个线程进去 reload 方法，其它线程返回旧的值。
     * </pre>
     */
    private void concurrentGetExpiredKeyAndRefreshCache() throws InterruptedException {
        cache.put("name", "mars");
        TimeUnit.SECONDS.sleep(3);

        int taskNum = 3;
        CountDownLatch downLatch = new CountDownLatch(taskNum);

        for (int i = 0; i < taskNum; i++) {
            threadPool.execute(() -> {
                long tid = Thread.currentThread().getId();
                System.out.printf("begin geet cache, thread = %d\n", tid);
                String cacheVal = cache.getUnchecked("name");
                System.out.printf("cache value: %s, thread = %d\n", cacheVal, tid);

                downLatch.countDown();
            });
        }
        downLatch.await();
        System.out.println("done");
    }

    public static void main(String[] args) throws InterruptedException {
        GuavaCacheExample cache = new GuavaCacheExample();
        cache.getExpiredKeyAndRefreshCache();
    }
}
