package com.maxzuo.collections;

import org.junit.Test;

import java.util.*;

/**
 * Map 家族类
 * <p>
 * Created by zfh on 2019/09/26
 */
public class MapExample {

    /**
     * HashMap源码分析
     * <pre>
     *   1.HashMap 底层是基于数组和链表实现的。
     *   2.容量的默认大小是 16，负载因子是 0.75，当 HashMap 的 size > 16*0.75 时就会发生扩容(容量和负载因子都可以自由调整)。
     * </pre>
     */
    @Test
    public void testHashMap () {
        HashMap<String, String> data = new HashMap<>(16);
        data.put("name", "dazuo");
        data.get("name");
        System.out.println(data.size());
        Set<Map.Entry<String, String>> entries = data.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
    }

    /**
     * HashSet源码分析（底层使用HashMap实现）
     */
    @Test
    public void testHashSet () {
        HashSet<String> data = new HashSet<>();
        data.add("name");
        data.add("age");
        String[] array = data.toArray(new String[0]);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 众所周知 HashMap 是一个无序的 Map，因为每次根据 key 的 hashcode 映射到 Entry 数组上，所以`遍历出来`的顺序并不是写入的顺序。
     * JDK 推出一个基于 HashMap 但具有顺序的 LinkedHashMap 来解决有排序需求的场景。
     *
     * 它的底层是继承于 HashMap 实现的，由一个双向链表所构成。
     *
     * LinkedHashMap 的排序方式有两种：
     *   1）根据写入顺序排序。
     *   2）根据访问顺序排序。
     */
    @Test
    public void testLinkedHashMap () {
        Map<String, String> data = new LinkedHashMap<>(10);
        data.put("name", "dazuo");
        data.put("age", "22");
        data.put("gender", "1");
        Set<String> set = data.keySet();
        for (String item : set) {
            System.out.println(item);
        }
    }

    /**
     * IdentityHashMap
     * <pre>
     *  1.两者最主要的区别是IdentityHashMap使用的是 == 比较key的值，而HashMap使用的是equals()
     *  2.IdentityHashMap中key能重复，但需要注意一点的是key比较的方法是==，所以若要存放两个相同的key，就需要存放不同的地址。
     *  3.IdentityHashMap有其特殊用途，比如序列化或者深度复制。或者记录对象代理。
     *    举个例子，jvm中的所有对象都是独一无二的，哪怕两个对象是同一个class的对象，而且两个对象的数据完全相同，对于jvm来说，
     *    他们也是完全不同的，如果要用一个map来记录这样jvm中的对象，你就需要用IdentityHashMap，而不能使用其他Map实现。
     * </pre>
     */
    @Test
    public void IdentityHashMap () {
        Map<Integer, Object> data = new IdentityHashMap<>(10);
        data.put(128, "dazuo");
        data.put(128, "dazuo2");
        System.out.println(data);
        // 输出：2
        System.out.println(data.size());
    }

    /**
     * TreeMap实现SortedMap接口，能够把它保存的记录根据键排序，默认是按键值的升序排序，也可以指定排序的比较器，
     * 当用Iterator遍历TreeMap时，得到的记录是排过序的。
     */
    @Test
    public void testTreeMap () {
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("abc", "abc");
        treeMap.put("23", "23");
        treeMap.put("cjk", "cjk");
        Set<Map.Entry<String, String>> entries = treeMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey());
        }
    }
}
