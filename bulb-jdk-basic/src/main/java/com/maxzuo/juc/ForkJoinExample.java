package com.maxzuo.juc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join框架简单使用
 * <p>
 * Created by zfh on 2019/04/17
 */
public class ForkJoinExample {

    /**
     * 计算1 ~ 100数字总和
     */
    public static void main(String[] args) {
        /*
            ForkJoinPool是Java 1.7之后新添加的一个ExecutorService实现，在java.util.concurrent中。和其他的ExecutorService一样，
            ForkJoinPool在提供自身特殊优势的同时也可以作为一个普通的Executor框架来使用，通过submit等方法来提交Runnable任务。

            ForkJoinPool最大的特殊之处就在于其实现了工作密取（work-stealing）。
         */
        ForkJoinPool pool = new ForkJoinPool();

        CountTask task = new CountTask(1, 10000);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * fork/join框架下的任务
 * <pre>
 *  RecurisiveTask：带有返回值的接口
 *  RecurisiveAction：没有返回值的接口
 * </pre>
 */
class CountTask extends RecursiveTask<Integer> {

    /**
     * 划分任务的标度
     */
    private static final int THREAD_HOLD = 2;

    private Integer          start;

    private Integer          end;

    public CountTask(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 如果任务足够小就计算
        boolean canCompute = (end - start) <= THREAD_HOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            CountTask left = new CountTask(start, middle);
            CountTask right = new CountTask(middle + 1, end);
            // 执行任务
            left.fork();
            right.fork();

            // join方法则等待任务完成并返回指向结果
            int lResult = left.join();
            int rResult = right.join();
            sum = lResult + rResult;
        }
        System.out.println("thread id = " + Thread.currentThread().getId());
        return sum;
    }
}