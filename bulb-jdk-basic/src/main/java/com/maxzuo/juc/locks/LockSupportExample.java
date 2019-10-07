package com.maxzuo.juc.locks;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 创建锁和其他同步类的基本线程阻塞原语。
 *
 * <p>这个类与使用它的每个线程关联一个许可证(在信号量类的意义上)。如果许可证可用，调用 pack() 将立即返回，并在此过程中使用它;
 * 否则它可能阻塞。如果尚未获得许可证，则调用unpark() 将使许可证可用。(与信号量不同，许可证不会累积。最多只有一个。)
 *
 * Created by zfh on 2019/04/14
 */
public class LockSupportExample {

    /**
     * park()：为线程调度的目的禁用当前线程，除非许可证可用。如果许可证可用，则使用许可证并立即返回;否则，当前线程
     *        将出于线程调度的目的而被禁用，并处于休眠状态，直到发生以下三种情况之一:
     *        1）其他一些线程以当前线程为目标调用unpark;
     *        2）其他一些线程中断当前线程;
     *        3）虚假的调用(也就是说，没有任何理由)返回。
     *
     * unpark(Thread thread)：提供给定线程的许可证(如果它还没有可用)。如果线程在park上被阻塞，那么它将解锁。否则，
     *        它的下一个调用park是保证不阻塞。如果没有启动给定的线程，则不能保证此操作有任何效果
     */
    @Test
    public void testPackAndUnpack () {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 start ...");
                LockSupport.park();
                System.out.println("t1 end ...");
            }
        });
        t1.start();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);
        System.out.println("main ...");
    }

    /**
     * 阻塞当前线程，超时返回，阻塞时间最长不超过nanos纳秒
     */
    @Test
    public void testParkNanos () {
        // 1（ms） = 1000 * 1000（ns）
        long time = 1000 * 1000 * 1000 * 2;
        LockSupport.parkNanos(time);
        System.out.println("hello pack!");
    }

    /**
     * 阻塞当前线程，直到deadline时间点（单位：毫秒）
     */
    @Test
    public void testParkUntil () {
        long deadLine = System.currentTimeMillis() + 1000 * 2;
        LockSupport.parkUntil(deadLine);
        System.out.println("hello pack!");
    }

    /**
     * 线程快照（Junit会中断子线程，从而中断LockSupport.park()，干扰实验结果）
     * <pre>
     *   LockSupport.getBlocker()：返回提供给park方法的最近一次调用的blocker对象，该方法尚未被解除阻塞，如果未被阻塞，
     *   则返回null。返回的值只是一个瞬时快照——线程可能在不同的blocker对象上解除了阻塞或阻塞。
     * </pre>
     */
    public static void main(String[] args) throws InterruptedException {
        String snapshotValue = "123";
        /// 未阻塞
        // long deadLine = System.currentTimeMillis() + 1000 * 2;
        // LockSupport.parkUntil(snapshotValue, deadLine);
        // System.out.println("snapshotValue：" + LockSupport.getBlocker(Thread.currentThread()));

        // 阻塞
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("begin ...");
                LockSupport.park(snapshotValue);
                System.out.println("begin ...");
            }
        });
        thread.start();

        TimeUnit.SECONDS.sleep(2);

        Object blocker = LockSupport.getBlocker(thread);
        System.out.println("blocker: " + blocker);
    }
}
