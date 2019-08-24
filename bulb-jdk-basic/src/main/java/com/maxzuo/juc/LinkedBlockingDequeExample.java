package com.maxzuo.juc;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Deque和BlockingDeque,它们分别对Queue和BlockingQueue进行了扩展。
 * Deque是一个双端队列，deque(双端队列) 是 "Double Ended Queue" 的缩写。因此，双端队列是一个你可以从任意一端插入或者抽取元素的队列。
 * 实现了在队列头和队列尾的高效插入和移除。
 * BlockingDeque 类是一个双端队列，在不能够插入元素时，它将阻塞住试图插入元素的线程；在不能够抽取元素时，它将阻塞住试图抽取的线程。
 *
 * <pre>
 *  使用场景：
 *     正如阻塞队列使用与生产者-消费者模式，双端队列同样适用于另一种相关模式，即工作密取。在生产者-消费者设计中，所有消费者有一个
 *   共享的工作队列，而在工作密取设计中，每个消费者都有各自的双端队列。如果一个消费者完成了自己双端队列中的全部工作，那么它可以从
 *   其它消费者双端队列末尾秘密地获取工作。密取工作模式比传统的生产者-消费者模式具有更高的可伸缩性，这是因为工作者线程不会在单个共
 *   享的任务队列上发生竞争。在大多数时候，它们都只是访问自己的双端队列，从而极大地减少了竞争。当工作者线程需要访问另一个队列时，它
 *   会从队列的尾部而不是头部获取工作，因此进一步降低了队列上的竞争程度。
 * </pre>
 *
 * 文章参考：https://www.cnblogs.com/duanxz/p/3169362.html
 * <p>
 * Created by zfh on 2019/03/25
 */
public class LinkedBlockingDequeExample {

    private static final LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>(20);

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
                        deque.add("hello world");
                        deque.add("hello world");
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
                        System.out.println("before deque size：" + deque.size());

                        // 取不到元素，一直阻塞到取到元素
                        Object take = deque.take();
                        System.out.println("take element : " + take);

                        System.out.println("after deque size：" + deque.size());
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
                deque.put("hello world");
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
