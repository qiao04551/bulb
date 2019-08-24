package com.maxzuo.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 探索线程池
 * <pre>
 * 阿里开发规约
 *  1.创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。
 *  2.线程资源必须通过线程池提供，不允许在应用中自行显示创建线程。
 *  3.线程池不允许使用Executors，而是通过ThreadPoolExecutor的方式创建，这样的处理方式能更加准确线程池的运行规则，规避资源耗尽的风险。
 * </pre>
 * Created by zfh on 2019/01/22
 */
public class ThreadPoolExample {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExample.class);

    public static void main(String[] args) throws InterruptedException {
        customThreadPool();

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * 自定义线程池
     */
    private static void customThreadPool() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 10, 60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                new NamedThreadFactory("一号机房"),
                new RejectedHandlerExample());

        // submit()方法，可以提供Future < T > 类型的返回值。
        Future<?> future = threadPool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println("线程中断异常");
                }
                return "hello call";
            }
        });
        // 询问是否执行完毕
        while (!future.isDone()) {
            try {
                System.out.println("result：" + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // executor()方法，无返回值。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello two");
            }
        });
    }

    /**
     * 线程池的几种工作队列
     */
    private static void threadPoolBlockQueue () {
        // 基于链表结构的有界阻塞队列，此队列按FIFO （先进先出） 排序元素
        // 示例：Executors.newFixedThreadPool()和Executors.newSingleThreadExecutor()
        new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

        // 一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态
        // 示例：Executors.newCachedThreadPool()
        new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, new SynchronousQueue<>());

        // new ScheduledThreadPoolExecutor(5) 使用的是专用的 DelayedWorkQueue 延迟队列（包内可见）

        // 一个支持线程优先级排序的无界队列，默认自然序进行排序，也可以自定义实现compareTo()方法来指定元素排序规则，不能保证同优先级元素的顺序。
        new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, new PriorityBlockingQueue<>());

        // 基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
        new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        // 一个由链表结构组成的双向阻塞队列。队列头部和尾部都可以添加和移除元素，多线程并发时，可以将锁的竞争最多降到一半
        new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
    }

    /**
     * 常见的线程池及使用场景
     */
    private static void ExecutorService() {
        // 核心线程数量，也是最大线程数量，不存在空闲线程
        // 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
        Executors.newFixedThreadPool(1);

        // 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
        // 当线程执行中出现异常，去创建一个新的线程替换之
        Executors.newSingleThreadExecutor();

        // 线程数最大Integer.MAX_VALUE，是高度可伸缩的线程池，存在OOM风险。keepAliveTime默认为60秒，工作线程处于空闲状态，
        // 则回收工作线程。如果任务数增加，再次创建新线程处理任务。
        Executors.newCachedThreadPool();

        // 可以将定时任务与线程池功能结合使用。
        ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(5);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    logger.error("schedule exception", e);
                }
            }
        }, 0, 10, TimeUnit.SECONDS);

        // JDK 8 新增的一种线程池，获取当前可用的线程数量进行创建作为并行级别，底层调用的是ForkJoinPool线程池
        // 创建一个拥有多个任务队列的线程池，可以减少连接数，创建当前可用cpu数量的线程来并行执行，适用于大耗时的操作，可以并行来执行
        Executors.newWorkStealingPool();
    }
}
