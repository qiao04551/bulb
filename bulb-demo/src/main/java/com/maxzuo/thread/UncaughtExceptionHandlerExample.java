package com.maxzuo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 使用Thread.UncaughtExceptionHandler来处理线程内部的非受检异常（受检异常在run()方法体内部的catch子句中处理，不支持往上抛）
 * <p>
 * Created by zfh on 2019/07/13
 */
public class UncaughtExceptionHandlerExample {

    private static final Logger logger = LoggerFactory.getLogger(UncaughtExceptionHandlerExample.class);

    public static void main(String[] args) {
        try {
            // 设置全局默认的异常处理
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

            Thread t = new Thread(new AdminTask());
            // 针对于该线程，设置异常捕获处理
            t.setUncaughtExceptionHandler(new ExceptionHandler());
            t.start();

            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            // TODO: 无法捕获子线程的非受检异常
            logger.error("发生异常！", e);
        }
    }

    /**
     * 任务
     */
    private static class AdminTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("AdminTask start ...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new NullPointerException("AdminThread 异常！");
        }
    }

    /**
     * 当线程由于未捕获异常突然终止时调用的处理程序的接口。
     *
     * 当一个线程由于未捕获异常即将终止时，Java虚拟机将使用getUncaughtExceptionHandler查询线程的UncaughtExceptionHandler，
     * 并调用处理程序的uncaughtException方法，将线程和异常作为参数传递。如果一个线程没有显式地设置它的UncaughtExceptionHandler，
     * 那么它的ThreadGroup对象就充当它的UncaughtExceptionHandler。如果ThreadGroup对象没有处理异常的特殊要求，它可以将调用转发
     * 给默认的未捕获异常处理程序。
     */
    private static class ExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Thread:" + t + " Exception message:" + e);
        }
    }
}
