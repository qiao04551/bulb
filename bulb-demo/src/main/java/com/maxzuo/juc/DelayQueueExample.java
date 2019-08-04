package com.maxzuo.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DelayQueue队列的使用
 * <pre>
 *   DelayQueue是一个支持延时获取元素的无界阻塞队列。队列使用PriorityQueue来实现。用于放置实现了Delayed接口的对象，其中的对象
 *   只能在其到期时才能从队列中取走。这种队列是有序的，即队头对象的延迟到期时间最长。注意：不能将null元素放置到这种队列中。
 * </pre>
 * <p>
 * Created by zfh on 2019/08/04
 */
public class DelayQueueExample {

    private static final Logger logger = LoggerFactory.getLogger(DelayQueueExample.class);

    private static DelayQueue<Task> taskDelayQueue = new DelayQueue<>();

    private static Executor executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        // 消费线程
        Thread consumeThread = new Thread(() -> {
            while (true) {
                try {
                    //从延迟队列中取值,如果没有对象过期则队列一直等待
                    Task t1 = taskDelayQueue.take();
                    logger.info("取得任务 " + t1.getNumber());
                    // 修改问题的状态
                    Runnable task = t1.getTask();
                    if (task == null) {
                        continue;
                    }
                    logger.info("执行任务 " + t1.getNumber());
                    executor.execute(task);
                    logger.info("延迟队列长度：" + taskDelayQueue.size());
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        consumeThread.setDaemon(true);
        consumeThread.start();

        // 生产线程
        AtomicInteger count = new AtomicInteger(0);
        Thread productThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 5; i > 0; i--) {
                    taskDelayQueue.put(new Task<>(i * 1000, new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("延迟任务" + LocalDateTime.now());
                        }
                    }, count.incrementAndGet()));
                }
            }
        });
        productThread.setDaemon(true);
        productThread.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延迟任务
     */
    private static class Task<T extends Runnable> implements Delayed {

        private final long time;

        private final T task;

        /**
         * 任务编号
         */
        private Integer number;

        public Task (long timeout, T t, Integer number) {
            this.time = System.currentTimeMillis() + timeout;
            this.task = t;
            this.number = number;
        }

        public T getTask() {
            return this.task;
        }

        /**
         * 返回与此对象相关的剩余延迟时间，以给定的时间单位表示
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return this.time - System.currentTimeMillis();
        }

        /**
         * 任务按过期时间排序
         */
        @Override
        public int compareTo(Delayed other) {
            long d = getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }

        public Integer getNumber() {
            return number;
        }
    }
}
