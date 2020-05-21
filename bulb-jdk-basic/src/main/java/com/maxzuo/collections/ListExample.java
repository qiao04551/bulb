package com.maxzuo.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 集合框架-List
 * <p>
 * Created by zfh on 2019/10/19
 */
public class ListExample {

    /**
     * 基于数组实现，随机访问数组元素效率高，时间复杂度O(1)；删除数据开销很大，需要重排数组中所有的数据。根据最坏打算，时间复杂度是O(n)。
     * <pre>
     *   1.ArrayList 实现了 RandomAccess 接口，实现了这个接口的 List，那么使用for循环的方式获取数据会优于用迭代器获取数据。
     *   2.扩容策略：int newCapacity = oldCapacity + (oldCapacity >> 1);  扩大一半。
     * </pre>
     */
    @Test
    public void testArrayList () {
        ArrayList<String> list = new ArrayList<>(10);
        list.add("one");
        list.add("two");
        list.set(1, "three");
        list.add(2, "four");
        list.remove(0);

        System.out.println(list.get(0));

        list.clear();
    }

    /**
     * LinkedList中插入或删除的时间复杂度仅为O(1)，不支持高效的随机元素访问。
     */
    @Test
    public void testLinkedList () {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("hello");
        linkedList.addFirst("first");
        linkedList.addLast("last");

        linkedList.poll();
        linkedList.pop();

        System.out.println(linkedList.get(0));
    }
}
