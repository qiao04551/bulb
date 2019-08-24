package com.maxzuo.thread;

import java.util.concurrent.TimeUnit;

/**
 * Thread.interrupt()方法的使用
 * <p>
 * Created by zfh on 2019/04/17
 */
public class ThreadInterruptMethodExample {

    public static void main(String[] args) throws InterruptedException {
        methodTwo();
        System.out.println("main end ...");
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    /**
     * 如果线程处于被阻塞状态（如线程调用了thread.sleep、thread.join、thread.wait、1.5中的condition.await、
     * 以及可中断的通道上的 I/O 操作方法后可进入阻塞状态），则会在这些阻塞方法调用处抛出InterruptedException异常，
     * 并且在抛出异常后立即将线程的中断标示位清除，即重新设置为false。抛出异常是为了线程从阻塞状态醒过来，并在结束
     * 线程前让程序员有足够的时间来处理中断请求。
     *
     * @throws InterruptedException
     */
    private static void methodOne() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 start ...");
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("t1 end ...");
                } catch (InterruptedException e) {
                    // 输出：--t1-- RUNNABLE
                    System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    System.out.println("t1 线程中断：" + e.getMessage());
                }
            }
        }, "--t1--");
        t1.start();

        TimeUnit.SECONDS.sleep(2);
        // 输出：RUNNABLE
        System.out.println("t1 state：" + t1.getState());
        t1.interrupt();
    }

    /**
     * 如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。被设置中断标志的线程将继续正常运行，不受影响。
     *
     * @throws InterruptedException
     */
    private static void methodTwo() throws InterruptedException {
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 start ...");
                // 检查本线程的中断标志位
                while (!Thread.currentThread().isInterrupted()) {
                    // nothing to do
                }
                // 输出：RUNNABLE
                System.out.println("t2 state：" + Thread.currentThread().getState());
                System.out.println("t2 continue ...");

                /**
                 * Thread.interrupted()会做两步操作：
                 *   1.返回当前线程的中断状态.
                 *   2.将当前线程的中断状态设为false.
                 */
                boolean interrupted = Thread.interrupted();
                System.out.println("interrupted: " + interrupted);

                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("t2 我又活了");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        System.out.println("发生异常");
                    }
                }
            }
        });
        t2.start();

        TimeUnit.SECONDS.sleep(2);
        t2.interrupt();
    }
}
