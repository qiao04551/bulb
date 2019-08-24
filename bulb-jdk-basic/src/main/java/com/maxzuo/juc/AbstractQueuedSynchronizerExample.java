package com.maxzuo.juc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * AbstractQueuedSynchronizer（AQS）
 * <pre>
 *   1.类如其名，抽象的队列式的同步器，AQS定义了一套多线程访问共享资源的同步器框架，许多同步类实现都依赖于它，
 *     如常用的ReentrantLock/Semaphore/CountDownLatch...。
 *
 *   2.它维护了一个volatile int state（代表共享资源）和一个FIFO线程等待队列（多线程争用资源被阻塞时会进入此队列）state的访问方式有三种:
 *      1）getState()
 *      2）setState()
 *      3）compareAndSetState()
 *
 *   3.AQS定义两种资源共享方式：Exclusive（独占，只有一个线程能执行，如ReentrantLock）和Share（共享，多个线程可同时执行，如Semaphore/CountDownLatch/CyclicBarrier）
 *
 *      不同的自定义同步器争用共享资源的方式也不同。自定义同步器在实现时只需要实现共享资源state的获取与释放方式即可，至于具体线程等待队列的维护
 *     （如获取资源失败入队/唤醒出队等），AQS已经在顶层实现好了。自定义同步器实现时主要实现以下几种方法：
 *
 *        1）isHeldExclusively()：该线程是否正在独占资源。只有用到condition才需要去实现它。
 *        2）tryAcquire(int)：独占方式。尝试获取资源，成功则返回true，失败则返回false。
 *        3）tryRelease(int)：独占方式。尝试释放资源，成功则返回true，失败则返回false。
 *        4）tryAcquireShared(int)：共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
 *        5）tryReleaseShared(int)：共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。
 *
 *   4.案例分析：
 *      1）以ReentrantLock为例，state初始化为0，表示未锁定状态。A线程lock()时，会调用tryAcquire()独占该锁并将state+1。此后，
 *         其他线程再tryAcquire()时就会失败，直到A线程unlock()到state=0（即释放锁）为止，其它线程才有机会获取该锁。当然，释放锁之前，
 *         A线程自己是可以重复获取此锁的（state会累加），这就是可重入的概念。但要注意，获取多少次就要释放多么次，这样才能保证state是能回到零态的。
 *
 *      2）以CountDownLatch以例，任务分为N个子线程去执行，state也初始化为N（注意N要与线程个数一致）。这N个子线程是并行执行的，
 *         每个子线程执行完后countDown()一次，state会CAS减1。等到所有子线程都执行完后(即state=0)，会unpark()主调用线程，然后
 *         主调用线程就会从await()函数返回，继续后余动作。
 *
 *   5.参考文章：https://www.cnblogs.com/waterystone/p/4920797.html
 * </pre>
 * Created by zfh on 2019/08/24
 */
public class AbstractQueuedSynchronizerExample {

    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private static final Mutex mutex = new Mutex();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    mutex.lock();
                    try {
                        sayHello();
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        mutex.unlock();
                    }
                }
            });
        }
        pool.shutdown();
    }

    private static void sayHello () {
        System.out.println("hello world");
    }
}

/**
 * Mutex是一个不可重入的互斥锁实现。锁资源（AQS里的state）只有两种状态：0表示未锁定，1表示锁定。
 */
class Mutex implements Lock, java.io.Serializable {

    // 自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {

        // 判断是否锁定状态
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 尝试获取资源，立即返回。成功则返回true，否则false。
        public boolean tryAcquire(int acquires) {
            // 这里限定只能为1个量
            assert acquires == 1;
            // state为0才设置为1，不可重入！
            if (compareAndSetState(0, 1)) {
                // 设置为当前线程独占资源
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 尝试释放资源，立即返回。成功则为true，否则false。
        protected boolean tryRelease(int releases) {
            // 限定为1个量
            assert releases == 1;
            //既然来释放，那肯定就是已占有状态了。只是为了保险，多层判断！
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            // 释放资源，放弃占有状态
            setState(0);
            return true;
        }

        // Provides a Condition
        Condition newCondition() { return new ConditionObject(); }

        // Deserializes properly
        private void readObject(ObjectInputStream s)
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    // 真正同步类的实现都依赖继承于AQS的自定义同步器！
    private final Sync sync = new Sync();

    // 获取资源，即便等待，直到成功才返回。
    public void lock(){
        sync.acquire(1);
    }

    // 尝试获取资源，要求立即返回。成功则为true，失败则为false。
    public boolean tryLock(){
        return sync.tryAcquire(1);
    }

    // 释放资源
    public void unlock(){
        sync.release(1);
    }

    public Condition newCondition(){
        return sync.newCondition();
    }

    // 锁是否占有状态
    public boolean isLocked()         {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }
}
