package com.maxzuo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 守护线程示例
 * 假设有二个线程，一个是常规的用户线程，不停写入日志，另一个是守护线程，在空闲时清理日志（仅保留最近的5条日志）
 * <pre>
 *  守护线程
 *   1.java中的守护线程(Daemon Thread) 指的是一类特殊的Thread，其优先级特别低(低到甚至可以被JVM自动终止)，通常这类线程用于在
 *     空闲时做一些资源清理类的工作，如JVM的垃圾回收、内存管理等线程。
 *   2.当所有的用户线程结束生命周期，那么JVM就会退出，进而守护线程也会退出。
 * </pre>
 * Created by zfh on 2019/02/20
 */
public class DeamonThreadTest {

    private static int queueCapacity = 100;

    private static BlockingQueue<String> logQueue = new ArrayBlockingQueue<>(queueCapacity);

    public static void main(String[] args) throws InterruptedException {
        LogWriter writer = new LogWriter("writer");
        LogCleaner cleaner = new LogCleaner("cleaner");
        cleaner.setDaemon(true);

        writer.start();
        // 1.守护线程优先级底，线程竞争时，writer线程拥有更大的几率优先执行。
        // 2.当所有的用户线程结束生命周期，那么JVM就会退出，进而守护线程也会退出。
        cleaner.start();
        Thread.sleep(10000);
    }

    /**
     * 模拟不停写日志（直到队列写满）
     */
    private static class LogWriter extends Thread {

        LogWriter(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            for (int i = 0; i < queueCapacity; i++) {
                try {
                    logQueue.put("" + i);
                    System.out.println(Thread.currentThread().getName() + " 日志已写入，当前日志内容：" + logQueue);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 模拟在空闲时清理日志（仅保留5条日志）
     */
    private static class LogCleaner extends Thread {

        LogCleaner (String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            while (true) {
                if (logQueue.size() > 5) {
                    try {
                        logQueue.take();
                        System.out.println(Thread.currentThread().getName() + " 多余日志被清理，当前日志内容：" + logQueue);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
