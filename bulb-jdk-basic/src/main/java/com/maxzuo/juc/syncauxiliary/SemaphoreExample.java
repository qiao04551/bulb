package com.maxzuo.juc.syncauxiliary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 同步辅助类-信号量Semaphore
 * <p>
 * Created by zfh on 2019/02/24
 */
public class SemaphoreExample {

    /**
     * 请求总数
     */
    private final static Integer CLIENT_TOTAL = 1000;

    /**
     * 同时并发执行的线程数
     */
    private final static Integer THREAD_TOTAL = 100;

    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
        for (int i = 0; i < CLIENT_TOTAL; i++) {
            threadPool.execute(() -> {
                try {
                    // 获取一个许可（当100个许可 获取完后，新的线程需要等待其它线程释放许可）
                    semaphore.acquire();
                    add();
                    // 释放一个许可
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void add() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " i = " + count.incrementAndGet());
        Thread.sleep(1000);
    }
}
