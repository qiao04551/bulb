package com.maxzuo.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Synchronize对象锁和类锁
 *
 * <p>1.java的内置锁
 * 每个java对象都可以用做一个实现同步的锁，这些锁称为内置锁。线程进入同步代码块或方法的时候会自动获得该锁，在退出同步代码块
 * 或方法时会释放该锁。获得内置锁的唯一途径就是进入这个锁的保护的同步代码块或方法。java内置锁是一个互斥锁，最多只有一个线程
 * 能够获得该锁。当上一个线程获取了锁，下一个线程将被阻塞，如果上一个线程不释放锁，下一个线程将永久等待。当上一个线程发生异
 * 常时，会自动释放线程占有的锁。
 *
 * <p>2.对象锁
 * 1）Java中类的对象可以有多个，每个对象都会有一个锁，或者叫监听器（monitor）互不干扰，当一个线程访问某个对象的synchronized
 *    方法时，将该对象上锁，其他任何线程都无法再去访问该对象所有的synchronized方法，直到释放锁。
 * 2）获取锁
 *    a.使用synchronized修饰非静态方法
 *    b.使用synchronized修饰代码块，对象锁是this或指定的对象；示例：synchronized(this|object) {}
 *    注意：代码块不是指类中代码块和静态代码块，而是方法内部的代码块。类锁同理。
 *
 * <p>3.类锁
 * 1）在Java中，针对每个类也有一个锁，可以称为“类锁”，类锁实际上是通过对象锁实现的，即类的Class对象锁，每个类只有一个类锁。
 * 2）获取锁
 *    a.使用synchronized修饰静态方法
 *    b.使用synchronized修饰代码块，对象锁是指定类的类对象；示例：synchronized(类.class) {}
 */
public class SychronizedExample {

    /**
     * 成员变量
     */
    private final Map<String, String> data = new HashMap<>(10);

    public static void main(String[] args) {
        SychronizedExample sExample = new SychronizedExample();
        sExample.assignment();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sExample.methodFive("t1");
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sExample.methodFive("t2");
            }
        });
        t2.start();
    }

    /**
     * 静态方法（类锁）
     */
    private synchronized static void methodOne(String threadName) {
        try {
            System.out.println("this is methodOne begin -------" + threadName);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("this is methodOne end -------" + threadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步代码块（类锁）
     */
    private static void methodTwo(String threadName) {
        synchronized (SychronizedExample.class) {
            try {
                System.out.println("this is methodTwo begin --------" + threadName);
                TimeUnit.SECONDS.sleep(2);
                System.out.println("this is methodTwo end --------" + threadName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 成员方法（对象锁）
     */
    private synchronized void methodThree(String threadName) {
        try {
            System.out.println("this is methodThree begin --------" + threadName);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("this is methodThree end --------" + threadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步代码块（对象锁）
     */
    private void methodFour(String threadName) {
        synchronized (this) {
            try {
                System.out.println("this is methodFour begin --------" + threadName);
                TimeUnit.SECONDS.sleep(2);
                System.out.println("this is methodFour end --------" + threadName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 赋值
     */
    private void assignment() {
        data.put("name", "dazuo");
    }

    /**
     * 成员变量
     */
    private void methodFive(String threadName) {
        String name = data.get("name");
        synchronized (name) {
            // 上面sync锁的值是 "dazuo", 此时进行修改，当另一个线程访问的时候，sync 锁住的值就是 "wang"
            // 因此两个方法的同步性已经被破坏了
            data.put("name", "wang");
            try {
                System.out.println("this is methodFive begin --------" + threadName);
                TimeUnit.SECONDS.sleep(2);
                System.out.println("this is methodFive end --------" + threadName);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
