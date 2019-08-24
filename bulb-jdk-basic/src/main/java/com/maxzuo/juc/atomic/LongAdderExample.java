package com.maxzuo.juc.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * JDK8 新增 LongAdder，该类也可以保证Long类型操作的原子性，相对于AtomicLong，LongAdder有着更高的性能和更好的表现，
 * 可以完全替代AtomicLong的来进行原子操作。
 * <p>
 * Created by zfh on 2019/08/24
 */
public class LongAdderExample {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.add(10);
        longAdder.increment();
        System.out.println(longAdder.longValue());
    }
}
