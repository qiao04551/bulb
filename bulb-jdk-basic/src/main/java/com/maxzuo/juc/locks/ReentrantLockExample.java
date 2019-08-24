package com.maxzuo.juc.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁 ReentrantLock
 * Created by zfh on 2019/03/12
 */
public class ReentrantLockExample {

    private static final Logger        logger = LoggerFactory.getLogger(ReentrantLockExample.class);

    private static final ReentrantLock LOCK   = new ReentrantLock();

    private static Integer             count  = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    task();
                }
            });
        }

    }

    /**
     * task body
     */
    private static void task() {
        LOCK.lock();
        try {
            System.out.format("count : %d threadId：%d \n", ++count, Thread.currentThread().getId());
        } catch (Exception e) {
            logger.error("发生异常", e);
        } finally {
            LOCK.unlock();
        }
    }
}
