package com.maxzuo.jmh;

/**
 * Created by zfh on 2019/06/04
 */
public interface Calculator {

    /**
     * calculate sum of an integer array
     * @param numbers
     * @return
     */
    public long sum(int[] numbers);

    /**
     * shutdown pool or reclaim any related resources
     */
    public void shutdown();
}
