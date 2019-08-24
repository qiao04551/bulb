package com.maxzuo.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference 用以支持对象的原子操作：AtomicReference<V> 可以封装引用一个V实例。
 * 可以支持并发访问，set的时候进行对比判断，如果当前值和操作之前一样则返回false，否则表示数据没有 变化。
 * Created by zfh on 2019/02/24
 */
public class AtomicReferenceExample {

    public static void main(String[] args) {
        AtomicReference<Integer> atomicReference = new AtomicReference<>(0);
        atomicReference.compareAndSet(0, 2);
        atomicReference.compareAndSet(2, 4);
        System.out.println(atomicReference.get());
    }
}
