package com.maxzuo.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 探索ThreadLocal对象
 * Created by zfh on 2019/01/22
 */
class ThreadLocalExample {

    @DisplayName("测试ThreadLocal")
    @Test
    void testThreadLocal() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("dazuo");
        System.out.println(Thread.currentThread().getId() + "：" + threadLocal.get());
        // 避免线程池的线程复用，使用完一定要清理，避免脏数据
        threadLocal.remove();
    }

    @Test
    void testInheritableThreadLocal () throws InterruptedException {
        ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

        // ThreadLocal传递到子线程
        inheritableThreadLocal.set("age");
        Thread subThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sub-thread:" + inheritableThreadLocal.get());
                // 子线程清理不会影响上一级的值（因为浅拷贝是key和value，清除的是新对象Map里面的）
                inheritableThreadLocal.remove();
                Thread subSubThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("sub-sub-thread：" + inheritableThreadLocal.get());
                    }
                });
                subSubThread.start();
            }
        });
        subThread.start();
        Thread.sleep(3000);
        // 输出：age
        System.out.println("outer：" + inheritableThreadLocal.get());
    }

    /**
     * <pre>
     * JDK的InheritableThreadLocal类可以完成父线程到子线程的值传递。但对于使用线程池等会池化复用线程的组件的情况，
     * 线程由线程池创建好，并且线程是池化起来反复使用的；这时父子线程关系的ThreadLocal值传递已经没有意义，应用需要
     * 的实际上是把 任务提交给线程池时的ThreadLocal值传递到 任务执行时。
     *
     * 解决方案：https://github.com/alibaba/transmittable-thread-local#-user-guide
     * </pre>
     */
    @Test
    void testTransmittableThreadLocal() throws InterruptedException {
        // 1.简单使用
        TransmittableThreadLocal<Integer> transmittableThreadLocal = new TransmittableThreadLocal<>();
        transmittableThreadLocal.set(100);
        System.out.println(transmittableThreadLocal.get());

        // 2.修饰Runnable
        Thread thread = new Thread(TtlRunnable.get(new Runnable() {
            @Override
            public void run() {
                System.out.println("sub-thread：" + transmittableThreadLocal.get());
                transmittableThreadLocal.remove();
            }
        }));
        thread.start();

        // TODO: 3.修饰线程池
    }
}
