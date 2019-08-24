package com.maxzuo.juc.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 读写锁
 * <pre>
 *   1.readLock()和writeLock()用来获取读锁和写锁。
 *   2.如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
 *   3.如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。
 * </pre>
 * <p>
 * Created by zfh on 2019/08/24
 */
public class ReentrantReadWriteLockExample {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        new Thread(){
            public void run() {
                sayHello(Thread.currentThread());
            };
        }.start();

        new Thread(){
            public void run() {
                sayHello(Thread.currentThread());
            };
        }.start();
    }

    private static void sayHello (Thread thread) {
        lock.readLock().lock();
        try {
            long start = System.currentTimeMillis();
            while(System.currentTimeMillis() - start <= 1) {
                System.out.println(thread.getName()+"正在进行读操作");
            }
            System.out.println(thread.getName()+"读操作完毕");
        } finally {
            lock.readLock().unlock();
        }
    }
}
