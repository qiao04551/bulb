package com.maxzuo.algorithm.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.HashSet;
import java.util.Set;

/**
 * 布隆过滤器
 * <pre>
 *   Bloom filter 是由 Howard Bloom 在 1970 年提出的二进制向量数据结构，它具有很好的空间和时间效率，被用来检测一个元素
 *   是不是集合中的一个成员。如果检测结果为是，该元素不一定在集合中；但如果检测结果为否，该元素一定不在集合中。因此Bloom filter
 *   具有100%的召回率。这样每个检测请求返回有“在集合内（可能错误）”和“不在集合内（绝对不在集合内）”两种情况，可见 Bloom filter
 *   是牺牲了正确率以节省空间。
 * </pre>
 * Created by zfh on 2020/01/19
 */
public class BloomFilterExample {

    public static void main(String[] args) {
        guavaBloomFilter();
    }

    /**
     * guava实现布隆过滤器
     */
    private static void guavaBloomFilter () {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 10000000, 0.01);
        for (int i = 0; i < 10000000; i++) {
            bloomFilter.put(i);
        }
        System.out.println(bloomFilter.mightContain(20));
    }

    /**
     * 测试HashMap写1000万条数据，将堆内存写死
     *
     * -Xms64m -Xmx64m -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError
     */
    private static void hashMapHeap () {
        Set<Integer> hashset = new HashSet<>(10000000);
        for (int i = 0; i < 10000000; i++) {
            hashset.add(i);
        }
        System.out.println(hashset.size());
    }
}
