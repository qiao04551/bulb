package com.maxzuo.juc.syncauxiliary;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步辅助类-CountDownLatch使用案例：三个工人先全部干完活，老板才检查。
 * <pre>
 *  1.Java的concurrent包里面的CountDownLatch其实可以把它看作一个计数器，只不过这个计数器的操作是原子操作，同时只能有一个线程去操作
 *  这个计数器，也就是同时只能有一个线程去减这个计数器里面的值。
 *  2.你可以向CountDownLatch对象设置一个初始的数字作为计数值，任何调用这个对象上的await()方法都会阻塞，直到这个计数器的计数值被其他
 *  的线程减为0为止。
 * </pre>
 * Created by zfh on 2019/01/29
 */
public class CountDownLatchExample {

    public static void main(String[] args) {
        ExecutorService poolExecutor = Executors.newCachedThreadPool();

        // 闭锁
        CountDownLatch downLatch = new CountDownLatch(3);
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(random.nextInt(3) * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        downLatch.countDown();
                    }
                }
            });
        }
        try {
            System.out.println("等待三个线程执行完...");
            downLatch.await();
            System.out.println("done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        poolExecutor.shutdown();
    }
}
