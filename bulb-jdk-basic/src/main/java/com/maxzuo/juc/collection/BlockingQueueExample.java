package com.maxzuo.juc.collection;

import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * 阻塞队列 BlockingQueue
 * Created by zfh on 2019/02/20
 */
public class BlockingQueueExample {

    /**
     * 1.基于数组实现的一个阻塞队列，在创建ArrayBlockingQueue对象时必须制定容量大小。并且可以指定公平性与非公平性，
     * 默认情况下为非公平的，即不保证等待时间最长的队列最优先能够访问队列。
     */
    @Test
    public void testArrayBlockQueue () {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            // 向队尾存入元素，如果队列满，则等待
            queue.put("dazuo");
            // 从队首取元素，如果队列为空，则等待
            String take = queue.take();
            System.out.println(take);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.基于链表实现的一个阻塞队列，在创建LinkedBlockingQueue对象时如果不指定容量大小，则默认大小为Integer.MAX_VALUE。
     */
    @Test
    public void testLinkedBlockingQueue () {
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);
        /*
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
         */
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>(20);
    }

    /**
     * 3.以上2种队列都是先进先出队列，而PriorityBlockingQueue却不是，它会按照元素的优先级对元素进行排序，按照优先级顺序出队，
     * 每次出队的元素都是优先级最高的元素。注意，此阻塞队列为无界阻塞队列，即容量没有上限（通过源码就可以知道，它没有容器满
     * 的信号标志），前面2种都是有界队列。
     */
    @Test
    public void testPriorityBlockingQueue () {
        PriorityBlockingQueue<String> blockingQueue = new PriorityBlockingQueue<>(10);
    }

    /**
     * 4.DelayQueue 是基于PriorityQueue，一种延时阻塞队列，DelayQueue中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。
     *   DelayQueue也是一个无界队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，而只有获取数据的操作（消费者）才会被阻塞。
     */
    @Test
    public void testDelayQueue () {
        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
        // 投放任务，延迟 3000 ms
        delayQueue.put(new DelayTask(3000, "dazuo"));
        delayQueue.put(new DelayTask(2000, "wang"));

        try {
            // 阻塞，等待获取任务
            DelayTask task = delayQueue.take();
            String value = task.getValue();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 5.SynchronousQueue是无界的，是一种无缓冲的等待队列
     * <pre>
     *   但是由于该Queue本身的特性，在某次添加元素后必须等待其他线程取走后才能继续添加；可以认为SynchronousQueue是一个缓存值
     *   为1的阻塞队列，但是 isEmpty()方法永远返回是true，remainingCapacity() 方法永远返回是0，remove()和removeAll()
     *   方法永远返回是false，iterator() 方法永远返回空，peek()方法永远返回null。
     * </pre>
     */
    @Test
    public void testSynchronousQueue () {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (;;) {
                            TimeUnit.SECONDS.sleep(3);

                            // 如果队列为空，阻塞
                            String ret = queue.take();
                            System.out.println("takeValue: " + ret);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (;;) {
                            // 将指定的元素添加到此队列中，等待另一个线程接收它。
                            queue.put(String.valueOf(System.currentTimeMillis()));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/**
 * 延迟任务
 */
class DelayTask implements Delayed {

    private final long time;

    private final String value;

    DelayTask(long timeout, String value) {
        this.time = System.currentTimeMillis() + timeout;
        this.value = value;
    }

    public String getValue() {
        return this.value;
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
}