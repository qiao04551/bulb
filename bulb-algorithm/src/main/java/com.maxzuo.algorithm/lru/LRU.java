package com.maxzuo.algorithm.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存淘汰算法--LRU算法
 * <pre>
 *  LRU（Least recently used，最近最少使用）算法根据数据的历史访问记录来进行淘汰数据。
 *   1.新数据插入到链表头部；
 *   2.每当缓存命中（即缓存数据被访问），则将数据移到链表头部；
 *   3.当链表满的时候，将链表尾部的数据丢弃。
 * </pre>
 * Created by zfh on 2020/07/04
 */
public class LRU<K, V>{

    private final int capacity;

    private final Map<K, Pair<K, V>> cache;

    private Pair<K, V> first;

    private Pair<K, V> last;

    public LRU(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    public void put(K key, V value) {
        Pair<K, V> pair = cache.get(key);
        if (pair == null) {
            if (cache.size() >= capacity) {
                cache.remove(last.key);
                removeLast();
            }
            pair = new Pair<>();
            pair.key = key;
        }
        pair.value = value;
        cache.put(key, pair);
        moveToFirst(pair);
    }

    public V get(K key) {
        Pair<K, V> pair = cache.get(key);
        if (pair == null) {
            return null;
        }
        moveToFirst(pair);
        return pair.value;
    }

    private void moveToFirst(Pair<K, V> pair) {
        if (pair == first) {
            return;
        }
        if (pair.pre != null) {
            pair.pre.next = pair.next;
        }
        if (pair.next != null) {
            pair.next.pre = pair.pre;
        }
        if (pair == last) {
            last = last.pre;
        }
        if (first == null || last == null) {
            first = last = pair;
            return;
        }
        pair.next = first;
        first.pre = pair;
        pair.pre = null;
    }

    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }
    }

    private static class Pair<K, V> {
        Pair<K, V> pre;
        Pair<K, V> next;
        K key;
        V value;
    }

    public static void main(String[] args) {
        LRU<Integer, String> lru = new LRU<>(10);
        lru.put(1, "one");
        lru.put(2, "two");
        lru.put(3, "three");
        System.out.println(lru.get(2));
    }
}
