package com.maxzuo.basic;

import java.util.LinkedList;

/**
 * linkedList使用案例
 * Created by zfh on 2019/02/11
 */
public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("one");
        linkedList.add("two");
        linkedList.add("three");
        System.out.println("第一个元素：" + linkedList.getFirst());
        System.out.println("最后一个元素：" + linkedList.getLast());
        System.out.println("链表长度：" + linkedList.size());
    }
}
