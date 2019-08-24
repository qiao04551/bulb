package com.maxzuo.thread;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * Thread类的使用
 * <p>
 * Created by zfh on 2019/08/24
 */
class ThreadExample {

    /**
     * 线程的几种状态
     */
    @Test
    void testThreadStatus () {
        // 未启动
        Thread.State status = Thread.State.NEW;

        // 可运行线程的线程状态。处于可运行状态的线程正在Java虚拟机中执行，但它可能正在等待操作系统中的其他资源，比如处理器。
        status = Thread.State.RUNNABLE;

        // 等待监视器锁的阻塞线程的线程状态。处于阻塞状态的线程正在等待监视器锁输入同步块/方法，
        // 或者在调用Object#wait()后重新输入同步块/方法
        status = Thread.State.BLOCKED;

        // 处于等待状态的线程，正在等待另一个线程执行特定的操作：Object.wait()、Thread.join()、LockSupport.park()
        status = Thread.State.WAITING;

        // 具有指定等待时间的等待线程的线程状态。由于使用指定的正等待时间调用下列方法之一，线程处于定时等待状态
        status = Thread.State.TIMED_WAITING;

        // 终止线程的线程状态。线程已经完成执行。
        status = Thread.State.TERMINATED;
    }

    @DisplayName("创建线程")
    @Test
    void testCreateThread () {
        /*
           创建线程的三种方式：
            1）继承Thread类，重写run方法
            2）实现Runnable接口，重写run方法
            3）实现Callable接口，重写call方法

           三种方式的对比
            1）前两种方式都有一个共同的缺陷，即在任务执行完成后，无法直接获取执行结果，需要借助共享变量等获取。
               而Callable和Future可以在任务执行完毕之后得到任务执行结果。
            2）Callable的call()可以抛出异常，而Runnable只有通过setDefaultUncaughtExceptionHandler()的
               方式才能在主线程中捕获到子线程异常。
         */
    }

    @DisplayName("守护线程和用户线程")
    @Test
    void testDeamonThread () {
        /*
            线程分两种
              1.守护线程
              2.用户线程

            守护线程
              1.java中的守护线程(Daemon Thread) 指的是一类特殊的Thread，其优先级特别低(低到甚至可以被JVM自动终止)，通常这类线程用于在
                空闲时做一些资源清理类的工作，如JVM的垃圾回收、内存管理等线程。
              2.当所有的用户线程结束生命周期，那么JVM就会退出，进而守护线程也会退出。
              3.守护线程优先级低，线程竞争时，用户线程拥有更大的几率优先执行。
         */

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });
        // true 表示设为 守护线程
        t.setDaemon(true);
        t.start();
    }

    @DisplayName("Thread.interrupt()方法的使用")
    @Test
    void testThreadInterruptMethod () throws InterruptedException {
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


