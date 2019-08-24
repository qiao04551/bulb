package com.maxzuo.thread;

import java.util.concurrent.*;

/**
 * 探索Callable、Future和FutureTask
 *
 * @See http://www.cnblogs.com/dolphin0520/p/3949310.html
 * Created by zfh on 2019/1/22
 */
public class CallableAndFutureExample {

    public static void main(String[] args) {
        //testUseCallable();
        testFutureTask();
    }

    /**
     * Callable的使用
     */
    public static void testUseCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                System.out.println("threadId：" + Thread.currentThread().getId());
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
            // 阻塞中，使用的同一个线程
            callable.call();
            System.out.println("main thread execute done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FutureTask的使用
     */
    private static void testFutureTask() {
        Callable callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    System.out.println("threadId：" + Thread.currentThread().getId());
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "执行完毕";
            }
        };

        FutureTask<String> task = new FutureTask(callable);
        Thread t = new Thread(task);
        t.start();
        System.out.println("main threadId：" + Thread.currentThread().getId());
        System.out.println("开始执行！");
        try {
            // 使用的子线程，调用get()方法阻塞
            String result = task.get();
            System.out.println("result：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("阻塞完毕！");
    }

    /**
     * Callable配置线程池使用
     */
    private static void relationThreadPool () {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        // 1.使用Callable+Future获取执行结果（子线程）
        CallableTask task1 = new CallableTask();
        Future<Integer> result = threadPool.submit(task1);
        try {
            System.out.println("task执行结果：" + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2.使用Callable+FutureTask获取执行结果（子线程）
        CallableTask task2 = new CallableTask();
        FutureTask<Integer> futureTask = new FutureTask<>(task2);
        threadPool.submit(futureTask);
        try {
            System.out.println("futureTask执行结果：" + futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭线程池
        threadPool.shutdown();
    }
}

/**
 * 实现Callable接口的方式创建线程
 * Created by zfh on 2019/01/22
 */
class CallableTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("CallableTask threadId：" + Thread.currentThread().getId());
        Thread.sleep(3000);
        return 1;
    }
}