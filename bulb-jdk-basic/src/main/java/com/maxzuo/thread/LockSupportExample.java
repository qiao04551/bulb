package com.maxzuo.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。
 * <p>
 * Created by zfh on 2019/04/14
 */
public class LockSupportExample {

    public static void main(String[] args) {
        /*
            void park()                     阻塞当前线程，当调用unpark(Thread)方法或被中断，才能从park()返回
            void parkNanos(long nanos)      阻塞当前线程，超时返回，阻塞时间最长不超过nanos纳秒
            void parkUntil(long deadline)   阻塞当前线程，直到deadline时间点
            void unpark(Thread)             唤醒处于阻塞状态的线程
         */

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
}
