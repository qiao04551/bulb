package com.maxzuo.juc.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的特性
 * <pre>
 *  1.Condition中的await()方法相当于Object的wait()方法，Condition中的signal()方法相当于Object的notify()方法，
 *    Condition中的signalAll()相当于Object的notifyAll()方法。不同的是，Object中的这些方法是和同步锁捆绑使用的；
 *    而Condition是需要与互斥锁/共享锁捆绑使用的。
 *  2.Condition它更强大的地方在于：能够更加精细的控制多线程的休眠与唤醒。对于同一个锁，我们可以创建多个Condition，
 *    在不同的情况下使用不同的Condition。
 * </pre>
 * Created by zfh on 2019/03/12
 */
public class ConditionExample {

    private static final ReentrantLock LOCK     = new ReentrantLock();

    private static final Condition     STOP_ONE = LOCK.newCondition();

    private static final Condition     STOP_TWO = LOCK.newCondition();

    /**
     * 测试多个Condition控制多线程的休眠与唤醒
     */
    public static void main(String[] args) {
        // TODO: 使用线程池会有问题！！！  使用junit会有问题！！！

        // 线程1
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " ------- hello t1");

                try {
                    LOCK.lock();
                    // 等待 STOP_ONE 唤醒，当STOP_ONE.signal()执行后，并不会立即结束STOP_ONE.awit() ，需要等待其LOCK.unlock()
                    STOP_ONE.await();

                    System.out.println("STOP_ONE await 结束 ... ");

                    // 等待 STOP_TWO 环境；必须依次唤醒，否则t1一直等待下去

                    STOP_TWO.await();

                    System.out.println("STOP_TWO await 结束 ... ");

                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }

                System.out.println("t1 end ...");
            }
        });
        t1.start();

        // 线程2，使用STOP_ONE
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -------- hello t2");
                try {
                    TimeUnit.SECONDS.sleep(2);

                    LOCK.lock();
                    STOP_ONE.signal();

                    System.out.println("STOP_ONE signal 触发完毕 ... ");

                    System.out.println("t2 进入第二次等待 ... ");

                    TimeUnit.SECONDS.sleep(2);

                    System.out.println("t2 第二次 等待结束 ... ");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
                System.out.println("t2 end ...");
            }
        });
        t2.start();

        // 线程3，使用STOP_TWO
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -------- hello t3");
                try {
                    TimeUnit.SECONDS.sleep(8);
                    LOCK.lock();
                    STOP_TWO.signal();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
                System.out.println("t3 end ...");
            }
        });
        t3.start();

        System.out.println("main thread ... ");
    }
}
