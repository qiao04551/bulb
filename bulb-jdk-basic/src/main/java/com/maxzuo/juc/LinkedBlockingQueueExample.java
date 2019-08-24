package com.maxzuo.juc;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 基于链表的阻塞队列
 * Created by zfh on 2019/03/25
 */
public class LinkedBlockingQueueExample {

    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

    /**
     * 测试读 阻塞
     */
    @Test
    public void testReadBlocking() {

        Thread writeThread = new Thread("--- write thread ---") {
            @Override
            public void run() {
                try {
                    while (true) {
                        queue.add("hello world 1");
                        queue.add("hello world 2");
                        TimeUnit.SECONDS.sleep(3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        writeThread.start();

        Thread readThread = new Thread("--- read thread ---") {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("before deque size：" + queue.size());

                        // 取不到元素，一直阻塞到取到元素
                        Object take = queue.take();
                        System.out.println("take element : " + take);

                        System.out.println("after deque size：" + queue.size());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        readThread.start();

        try {
            System.out.println("hello main");
            TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试写 阻塞
     */
    @Test
    public void testWriteBlocking () {
        for (int i = 0; i < 30; i++) {
            try {
                System.out.println("hello i = " + i);
                // 插入阻塞，队列已满
                queue.put("hello world");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            System.out.println("hello main");
            TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
