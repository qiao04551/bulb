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
     * 线程的状态 {@link Thread.State}
     */
    @Test
    void testThreadStatus () {
        /*
            Thread.State.NEW：尚未启动的状态
            Thread.State.RUNNABLE：可运行线程的线程状态。处于可运行状态的线程正在Java虚拟机中执行，但它可能正在等待操作
                                   系统中的其他资源，比如处理器。
            Thread.State.BLOCKED ：等待监视器锁的阻塞线程的线程状态。处于阻塞状态的线程正在等待监视器锁输入同步块/方法，
                                   或者在调用Object#wait()后重新输入同步块/方法

            Thread.State.WAITING ：等待线程的线程状态。一个线程由于调用下列方法之一而处于等待状态：
                                   1）Object.wait()
                                   2）Thread.join()
                                   3）LockSupport.park()

            Thread.State.TIMED_WAITING：具有指定等待时间的等待线程的线程状态。线程处于定时等待状态，因为调用以下方法之一
                                   与指定的正等待时间：
                                   1）Thread.sleep(long)
                                   2）Object.wait(long)
                                   3）Thread.join(long)
                                   4）LockSupport.parkNanos()
                                   5）LockSupport.parkUntil()

            Thread.State.TERMINATED：终止线程的线程状态。线程已经完成执行。
         */

        Thread.State state = Thread.currentThread().getState();
        System.out.println(state);
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

    /**
     * Thread类方法
     * <pre
     *   Thread#start()：当调用start方法后，系统才会开启一个新的线程来执行用户定义的子任务，在这个过程中，会为相应的线程分配需要的资源。
     *   Thread#run()  ：该方法不需要用户来调用，当通过start方法启动一个线程后，当线程获得CPU执行时间，便进入run方法体去执行具体的任务。
     *   Thread#join() ：
     *     1）等待线程终止（主线程等待子线程的终止，再恢复执行）
     *     2）实际上调用join()是调用了Object的wait()，从而使用主线程进入阻塞状态。
     *     3）通过查看JDK源码可以得知，线程终止后的清理工作，会调用 lock.notify_all(thread) 唤醒所有等待thread锁的线程，
     *        意味着调用了join方法被阻塞的主线程会被唤醒；
     *
     *   Thread.sleep()：静态方法，使当前执行的线程休眠(临时停止执行)指定的毫秒数。线程不会失去任何监视器的所有权。
     *   Thread.yield()：静态方法，调用yield方法会让当前线程交出CPU权限，让CPU去执行其他的线程。同样不会释放锁。yield不能控制
     *                   具体的交出CPU的时间，另外，yield方法只能让拥有相同优先级的线程有获取CPU执行时间的机会。
     * </pre>
     */
    @Test
    void testThreadJoin () throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello t1");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        /*
            使用main线程暂停，等待t1线程执行完毕，再恢复执行。
            原理解析：
              1）实际上是 t1 对象调用了Object的wait()，使main线程进入了阻塞状态。
              2）通过查看JDK源码可以得知，线程终止后的清理工作，会调用 lock.notify_all(thread) 唤醒所有等待thread锁的线程，
                 意味着调用了join方法被阻塞的主线程会被唤醒；
         */
        t1.join();

        System.out.println("main");
    }

    /**
     * Thread#interrupt()
     * <pre>
     *   1.如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。被设置中断标志的线程将继续正常运行，不受影响。
     *   2.如果线程处于被阻塞状态（如线程调用了thread.sleep、thread.join、thread.wait、1.5中的condition.await、以及可中断
     *     的通道上的 I/O 操作方法后可进入阻塞状态），则会在这些阻塞方法调用处抛出InterruptedException异常，并且在抛出异常后立
     *     即将线程的中断标示位清除，即重新设置为false。抛出异常是为了线程从阻塞状态醒过来，并在结束线程前让程序员有足够的时间来
     *     处理中断请求。
     *   3.如果配合isInterrupted()能够中断正在运行的线程，因为调用interrupt方法相当于将中断标志为true，那么可以通过调用
     *     isInterrupted()判断中断标志是否被置位来中断线程的执行。
     *   4.Thread.interrupted()方法会做两步操作：
     * 	    1）返回当前线程的中断状态.
     * 	    2）将当前线程的中断状态设为false.
     * </pre>
     */
    @DisplayName("阻塞中的线程")
    @Test
    void testThreadInterrupt() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t1 start ...");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    // 输出：--t1-- RUNNABLE
                    System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    System.out.println("t1 线程中断：" + e.getMessage());
                }
            }
        }, "--t1--");
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("t1 state：" + t1.getState());
        t1.interrupt();

        TimeUnit.SECONDS.sleep(2);
    }

    @DisplayName("运行中的线程")
    @Test
    void methodTwo() throws InterruptedException {
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


