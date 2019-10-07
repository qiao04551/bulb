package com.maxzuo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Object的 wait()和 notify()/notifyAll()
 * <pre>
 *   1.wait() 与 notify/notifyAll 方法必须在Synchronized同步代码块中使用。
 *   2.当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
 *   3.当执行notify/notifyAll()方法时，会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执行完synchronized
 *     代码块的代码释放锁 或是中途遇到wait() ，再次释放锁。
 *   4.注意：notify/notifyAll()执行后，并不立即释放锁，而是要等到执行完临界区中代码后，再释放。当线程释放锁后，之前调用
 *     wait()的线程就会去竞争锁，如果其中一个线程获取了锁，就会继续往下执行退出Synchronized代码块。
 *   5.使用线程同步类。这些类使线程间的协调更加容易，支持了更加丰富的线程协调场景，逐步淘汰了使用Object的wait()和notify()
 *     进行同步的方式。主要代表为CountDownLatch、Seamphore、CyclicBarrier等。
 * </pre>
 * Created by zfh on 2019/03/09
 */
public class ObjectWaitAndNotifyExample {

    private static final Logger logger = LoggerFactory.getLogger(ObjectWaitAndNotifyExample.class);

    public static void main(String[] args) {
        Object object = new Object();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("income t1");

                        // 释放object对象锁，进入等待
                        object.wait();
                        System.out.println("t1 end");
                    } catch (Exception e) {
                        logger.error("t1 exception", e);
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    try {
                        System.out.println("income t2");

                        // 此时不会释放锁，而是要等到执行完临界区中代码后，再释放；t2释放锁后，t1竞争得到锁继续从wait()方法后执行。
                        object.notify();

                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("t2 end");
                    } catch (Exception e) {
                        logger.error("t2 exception", e);
                    }
                }
            }
        });

        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("t2 run");
            t2.start();
        } catch (Exception e) {
            logger.error("t2 exception", e);
        }
        System.out.println("main thread");
    }

    /**
     * 测试 wait() 方法的超时
     */
    private static void testWaitTimeout () throws InterruptedException {
        // 没有参数，或者timeout = 0，则有永久等待。
        Object obj = new Object();
        synchronized (obj) {
            obj.wait(0);
        }
    }
}
