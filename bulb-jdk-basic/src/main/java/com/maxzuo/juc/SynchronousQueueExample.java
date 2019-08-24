package com.maxzuo.juc;

import java.time.LocalDateTime;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue是无界的，是一种无缓冲的等待队列
 * <pre>
 *   但是由于该Queue本身的特性，在某次添加元素后必须等待其他线程取走后才能继续添加；可以认为SynchronousQueue是一个缓存值为1的阻塞队列，
 *   但是 isEmpty()方法永远返回是true，remainingCapacity() 方法永远返回是0，remove()和removeAll() 方法永远返回是false，iterator()
 *   方法永远返回空，peek()方法永远返回null。
 * </pre>
 * <p>
 * Created by zfh on 2019/04/18
 */
public class SynchronousQueueExample {

    public static void main(String[] args) {
        SynchronousQueue<Runnable> runnables = new SynchronousQueue<>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // 获取阻塞
                        TimeUnit.SECONDS.sleep(3);
                        Runnable take = runnables.take();
                        take.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        try {
            // 添加阻塞
            runnables.put(new Task());
            System.out.println("put first end ...");
            runnables.put(new Task());
            System.out.println("put second end ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        System.out.println("hello task --- " + LocalDateTime.now());
    }
}
