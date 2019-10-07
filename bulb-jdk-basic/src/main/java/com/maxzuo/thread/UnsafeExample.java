package com.maxzuo.thread;

import org.junit.Test;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;

/**
 * sun.misc.Unsafe类的应用解析
 *
 * <p>java不能直接访问操作系统底层，而是通过本地方法来访问。Unsafe类提供了硬件级别的原子操作
 *
 * <p>异常 java.lang.SecurityException: Unsafe ；因@CallerSensitive注解的存在，只有由主类加载器加载的类才能调用这个方法。
 *
 * <p>文档地址：https://docs.qq.com/doc/DWlVsQldyWkVDSE1y
 */
public class UnsafeExample {

    public static void main(String[] args) {
    }

    /**
     * 1.内存管理
     *
     * 典型应用：
     *   DirectByteBuffer是Java用于实现堆外内存的一个重要类，通常用在通信过程中做缓冲池，如在Netty、MINA等NIO框架中应用广泛。
     *   DirectByteBuffer对于堆外内存的创建、使用、销毁等逻辑均由Unsafe提供的堆外内存API来实现。{@link ByteBuffer#allocateDirect(int)}
     */
    @Test
    public void testAllocateMemory () {
        Unsafe unsafe = Unsafe.getUnsafe();
        /// 分配内存
        // unsafe.allocateInstance();

        /// 重新分配内存
        // unsafe.reallocateMemory();

        /// 拷贝内存
        // unsafe.copyMemory();

        /// 释放内存
        // unsafe.freeMemory();
    }

    /**
     * 2.CAS操作
     *
     * 典型应用：
     *   CAS在java.util.concurrent.atomic相关类、Java AQS、CurrentHashMap等实现上有非常广泛的应用。
     */
    @Test
    public void testCAS () {
        Unsafe unsafe = Unsafe.getUnsafe();
        // unsafe.compareAndSwapInt();
    }

    /**
     * 3.线程调度，包括线程挂起、恢复、锁机制等方法。示例 {@link java.util.concurrent.locks.LockSupport#park();}
     */
    @Test
    public void testPackAndUnpack () {
        Unsafe unsafe = Unsafe.getUnsafe();
        /*
            阻塞一个线程直到unpack()出现、当前线程被中断或者timeout时间到期。
            如果在调用pack()前，一个unpack()调用已经出现了，这里只计数。
            参数：
              isAbsolute：如果为true，timeout是相对于新纪元之后的毫秒。否则这个值就是超时前的纳秒数。这个方法执行时也可能不合理地返回(没有具体原因)
              timeout：可以是一个要等待的纳秒数，或者是一个相对于新纪元之后的毫秒数直到到达这个时间点。

            参考：
              java.util.concurrent.locks.LockSupport.parkNanos(long nanos)
              java.util.concurrent.locks.LockSupport.parkUntil(long deadline)
         */
        unsafe.park(false, 0L);

        // 恢复指定线程
        unsafe.unpark(Thread.currentThread());
    }

    /**
     * 4.Class相关，此部分主要提供Class和它的静态字段的操作相关方法，包含静态字段内存定位、定义类、定义匿名类、检验&确保初始化等。
     */
    @Test
    public void testClass () {
        Unsafe unsafe = Unsafe.getUnsafe();
        /// 获取给定静态字段的内存地址偏移量
        // unsafe.staticFieldOffset();
    }

    /**
     * 5.对象操作，对象成员属性相关操作及非常规的对象实例化方式等相关方法。
     */
    @Test
    public void testObject () {
        Unsafe unsafe = Unsafe.getUnsafe();
        /// 非常规的实例化
        // unsafe.allocateInstance(UnsafeExample.class);

        /// 返回对象成员属性在内存地址相对于此对象的内存地址偏移量
        // unsafe.objectFieldOffset(UnsafeExample.class.getDeclaredField("value"));
    }

    /**
     * 6.数组相关，定位数组中每个元素在内存中的位置。
     */
    @Test
    public void testArray () {
        Unsafe unsafe = Unsafe.getUnsafe();
        /// 返回数组第一个元素的偏移地址
        // unsafe.arrayBaseOffset();

        /// 返回数组中一个元素占用的大小
        // unsafe.arrayIndexScale();
    }

    /**
     * 7.内存屏障， 在Java 8中引入，用于定义内存屏障（也称内存栅栏，内存栅障，屏障指令等，是一类同步屏障指令，是CPU或
     *   编译器在对内存随机访问的操作中的一个同步点，使得此点之前的所有读写操作都执行后才可以开始执行此点之后的操作），
     *   避免代码重排序。
     *
     * 典型应用：
     *   在Java 8中引入了一种锁的新机制——StampedLock，它可以看成是读写锁的一个改进版本。StampedLock提供了一种乐观读锁的实现，
     *   这种乐观读锁类似于无锁的操作，完全不会阻塞写线程获取写锁，从而缓解读多写少时写线程“饥饿”现象。
     */
    @Test
    public void testMemoryFence () {
        Unsafe unsafe = Unsafe.getUnsafe();
        // 内存屏障，禁止load操作重排序。屏障前的load操作不能被重排序到屏障后，屏障后的load操作，不能被重排序到屏障前。
        unsafe.loadFence();
    }
}
