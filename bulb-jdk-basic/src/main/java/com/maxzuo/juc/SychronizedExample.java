package com.maxzuo.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Synchronize关键字使用
 * <pre>
 *   同步、重量级锁
 *   Synchroized的原理
 *   锁优化（自旋锁、轻量级锁、重量级锁、偏向锁）
 * </pre>
 * Created by zfh on 2019/04/06
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
    private synchronized static void methodOne (String threadName) {
        try {
            System.out.println("this is methodOne begin -------" + threadName);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("this is methodOne end -------" + threadName);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步代码块（类锁）
     */
    private static void methodTwo (String threadName) {
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
    private synchronized void methodThree (String threadName) {
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
    private void methodFour (String threadName) {
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
    private void assignment () {
        data.put("name", "dazuo");
    }

    /**
     * 成员变量
     */
    private void methodFive (String threadName) {
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
