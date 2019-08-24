package com.maxzuo.juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Atomic包的AtomicInteger的简单使用
 * Created by zfh on 2019/02/24
 */
public class AtomicIntegerExample {

    /**
     * 自增
     *
     * 底层还是unsafe.compareAndSwapInt()
     */
    @Test
    public void testAtomicStampedReference () {
        AtomicInteger value = new AtomicInteger(0);
        value.incrementAndGet();
        System.out.println(value.get());
    }

    /**
     * 比较并交换（CAS）
     *
     * CAS操作包含三个操作数——内存位置、预期原值及新值。执行CAS操作的时候，将内存位置的值与预期原值比较，如果相匹配，
     * 那么处理器会自动将该位置值更新为新值，否则，处理器不做任何操作。
     */
    @Test
    public void testCompareAndSet () {
        AtomicInteger value = new AtomicInteger(1);
        // 当前值等于预期的值，进行更新
        value.compareAndSet(1, 3);
        System.out.println(value.get());
    }

    /**
     * 解决Java中 CAS-ABA问题
     */
    @Test
    public void testCASABA () {
        int stamp = 100;
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(10,stamp);
        atomicStampedReference.compareAndSet(10, 11, stamp, --stamp);
        System.out.println(atomicStampedReference.getReference());
    }
}
