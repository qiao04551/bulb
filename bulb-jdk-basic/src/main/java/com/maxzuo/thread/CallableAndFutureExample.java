package com.maxzuo.thread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 探索Callable、Future和FutureTask
 */
public class CallableAndFutureExample {

    /**
     * Callable的使用
     */
    @Test
    public void testUseCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                System.out.println("call threadId：" + Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello call";
            }
        };
        try {
            System.out.println("main threadId：" + Thread.currentThread().getId());
            callable.call();

            System.out.println("main thread execute done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FutureTask的使用
     */
    @Test
    public void testFutureTask() {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "1234";
            }
        });
        Thread t = new Thread(futureTask);
        t.start();

        try {
            // get() 阻塞
            String result = futureTask.get();
            System.out.println("result：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Future、Callable、FutureTask在线程池中使用
     */
    @Test
    public void relationThreadPool () {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        // 1.使用Callable+Future获取执行结果（子线程）
        Future<Integer> result = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            }
        });
        try {
            System.out.println("task执行结果：" + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2.使用Callable+FutureTask获取执行结果（子线程）
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                return 999;
            }
        });
        threadPool.submit(futureTask);
        try {
            System.out.println("futureTask执行结果：" + futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}