package com.maxzuo.basic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列 BlockingQueue
 * Created by zfh on 2019/02/20
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        // 1.基于数组实现的一个阻塞队列，在创建ArrayBlockingQueue对象时必须制定容量大小。并且可以指定公平性与非公平性，默认情况下为
        // 非公平的，即不保证等待时间最长的队列最优先能够访问队列。
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);

        // 2.基于链表实现的一个阻塞队列，在创建LinkedBlockingQueue对象时如果不指定容量大小，则默认大小为Integer.MAX_VALUE。
        //BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);

        // 3.以上2种队列都是先进先出队列，而PriorityBlockingQueue却不是，它会按照元素的优先级对元素进行排序，按照优先级顺序出队，
        // 每次出队的元素都是优先级最高的元素。注意，此阻塞队列为无界阻塞队列，即容量没有上限（通过源码就可以知道，它没有容器满
        // 的信号标志），前面2种都是有界队列。
        //BlockingQueue<String> blockingQueue = new PriorityBlockingQueue<>(10);

        // 4.DelayQueue：基于PriorityQueue，一种延时阻塞队列，DelayQueue中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。
        // DelayQueue也是一个无界队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，而只有获取数据的操作（消费者）才会被阻塞。

        try {
            // 向队尾存入元素，如果队列满，则等待
            blockingQueue.put("dazuo");
            // 从队首取元素，如果队列为空，则等待
            String element = blockingQueue.take();
            System.out.println("element: " + element);

            // 来向队尾存入元素，如果队列满，则等待一定的时间，当时间期限达到时，如果还没有插入成功，则返回false；否则返回true；
            blockingQueue.offer("age");
            // 用来从队首取元素，如果队列空，则等待一定的时间，当时间期限达到时，如果取到，则返回null；否则返回取得的元素；
            element = blockingQueue.poll();
            System.out.println("element: " + element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
