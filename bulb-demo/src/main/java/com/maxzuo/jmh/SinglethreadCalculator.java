package com.maxzuo.jmh;

/**
 * 串行计算
 * <p>
 * Created by zfh on 2019/06/04
 */
public class SinglethreadCalculator implements Calculator {

    public long sum(int[] numbers) {
        long total = 0L;
        for (int i : numbers) {
            total += i;
        }
        return total;
    }

    @Override
    public void shutdown() {
        // nothing to do
    }
}
