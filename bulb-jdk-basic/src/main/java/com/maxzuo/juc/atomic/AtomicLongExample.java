package com.maxzuo.juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicLong原子类
 * Created by zfh on 2019/03/25
 */
public class AtomicLongExample {

    private static final AtomicLong atomicLong = new AtomicLong(10);

    /**
     * 获取前一个值
     */
    @Test
    public void testPreviousValue() {
        System.out.println("当前值：" + atomicLong.get());
        long andIncrement = atomicLong.getAndIncrement();
        System.out.println("previous value: " + andIncrement);
        System.out.println("当前值：" + atomicLong.get());
    }

    /**
     * 更新并返回当前值
     */
    @Test
    public void testUpdateValue() {
        long currentValue = atomicLong.incrementAndGet();
        System.out.println("currentValue：" + currentValue);
        System.out.println("get currentValue: " + atomicLong.get());
    }
}
