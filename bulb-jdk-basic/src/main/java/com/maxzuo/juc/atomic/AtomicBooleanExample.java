package com.maxzuo.juc.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AtomicBoolean的使用
 * Created by zfh on 2019/02/24
 */
public class AtomicBooleanExample {

    public static void main(String[] args) {
        // 默认初始值：false
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        atomicBoolean.compareAndSet(false, true);
        System.out.println(atomicBoolean.get());
    }
}
