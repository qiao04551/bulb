package com.maxzuo.basic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * java.util.Arrays源码解析；以及JDK 8新特性的并行排序Arrays.parallelSort()...
 * <p>
 * Created by zfh on 2019/06/05
 */
class ArraysClassExample {

    /**
     * 使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方 法，它的 add/remove/clear 方法会抛出
     * UnsupportedOperationException 异常。 说明:asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。
     * Arrays.asList 体现的是适配器模式，只是转换接口，后台的数据仍是数组。
     */
    @Test
    void testArraysAsListMethod() {
        String[] array = new String[]{"name", "age"};
        List list = Arrays.asList(array);

        /// 下面一行代码执行会抛异常
        // list.add("sex");

        // 当修改数组时，list.get(0)随之修改
        array[0] = "name2";

        System.out.println("array: " + Arrays.toString(array));
        System.out.println("list: " + list);

        // 解决不能使用add，remove方法的局限性
        List<String> newList = new ArrayList<>(Arrays.asList(array));
        System.out.println("before newList: " + newList);

        // 新数组开辟一块新的内存空间
        newList.set(0, "dazuo");
        System.out.println("after newList: " + newList);
        System.out.println("after array：" + Arrays.toString(array));
    }

    /**
     * 复制元素，证明Arrays.copyOf() 是地址拷贝。
     */
    @Test
    void testArraysCopyOf () {
        User[] originArray = new User[]{new User("name", 22), new User("age", 32)};
        User[] newArray = Arrays.copyOf(originArray, 1, User[].class);
        System.out.println("before newArray：" + Arrays.toString(newArray));

        // 修改原数组，对新数组有影响。说明，Arrays.copyOf()是元素地址拷贝。
        originArray[0].setAge(23);
        System.out.println("after newArray：" + Arrays.toString(newArray));
    }

    /**
     * 将List转换为Array
     */
    @Test
    void testConvertListToArray () {
        List<User> originList = new ArrayList<>(10);
        originList.add(new User("dazuo", 22));
        originList.add(new User("wang", 23));

        // toArray实质是调用了Arrays.copyOf()生成新的数组，然后依次将元素复制过去
        Object[] objects = originList.toArray();

        // 推荐使用下面这种方式，可以指定类型
        User[] newArray = originList.toArray(new User[0]);
        System.out.println("before newArray: " + Arrays.toString(newArray));

        // 修改原数组，新数组受影响（Arrays.copyOf()是元素地址拷贝)
        originList.get(0).setAge(24);
        System.out.println("after newArray: " + Arrays.toString(newArray));

        // 同理，修改数组，原list也受影响
        newArray[0].setAge(29);
        System.out.println("after originList: " + originList);
    }

    /**
     * 使用指定的元素填充整个数组（会替换掉数组中原来的元素）
     */
    @Test
    void testArraysFillMethod () {
        String[] userList = {"dazuo", "wang", "yu"};
        System.out.println("before userList: " + Arrays.toString(userList));

        Arrays.fill(userList, "dazuo");
        System.out.println("after userList: " + Arrays.toString(userList));
    }

    /**
     * 基本类型数组的排序
     * <pre>
     *   1.数组元素少于阀值INSERTION_SORT_THRESHOLD（47）就用插入排序。
     *   2.数组元素大于INSERTION_SORT_THRESHOLD（47）小于QUICKSORT_THRESHOLD（286），用一种快速排序的方法：
     *     1）从数列中挑出五个元素，称为 “基准”（pivot）；
     *     2）重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
     *     3）递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
     *   3.数组元素大于小于QUICKSORT_THRESHOLD（286）进入归并排序。
     *
     *
     * </pre>
     */
    @Test
    void testBasicTypeArraysSort () {
        int[] list =  {2, 4, 1, 3, 5};
        Arrays.sort(list);
        System.out.println(Arrays.toString(list));
    }

    /**
     * 对象数组的排序
     * <pre>
     *   对象数组的比较会使用归并排序legacyMergeSort() 或 Tim排序 来进行排序。
     *   Timsort是结合了合并排序（merge sort）和插入排序（insertion sort）而得出的排序算法，它在现实中有很好的效率。
     * </pre>
     */
    @Test
    void testObjectTypeArraysSort () {
        String[] data = {"1", "4", "3", "2"};
        Arrays.sort(data);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 并行排序（Jdk 8），使用了Fork/Join框架
     */
    @Test
    void testParallelSort () {
        String[] data = {"1", "4", "3", "2"};
        Arrays.parallelSort(data);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 使用自定义比较器，对数组元素进行排序
     */
    @Test
    void testCustomArraysSort () {
        User[] users = {new User("dazuo", 23), new User("wang", 22), new User("gao", 18)};
        // 按照年龄从小到大排序，返回-1放左边，1放右边，0保持不变
        Arrays.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        System.out.println("users: " + Arrays.toString(users));
    }

    private static class User {

        private String name;

        private Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    /**
     * 使用 二分法 查找数组内指定元素的索引值
     * <pre>
     *   注意：在调用该方法之前，必须先调用sort()方法进行排序，如果数组没有排序，
     *   那么结果是不确定的，此外如果数组中包含多个指定元素，则无法保证将找到哪个元素
     * </pre>
     */
    @Test
    void testBinarySearch () {
        String[] userList = {"dazuo", "abc", "eed", "ccd", "wang", "uu"};
        Arrays.sort(userList);
        int index = Arrays.binarySearch(userList, "eed");
        System.out.println("index: " + index);
    }

    /**
     * 数组的哈希值，数组的比较
     */
    @Test
    void testArrayHashCodeAndArraysEquals () {
        String[] userList = {"dazuo", "wang", "uu"};
        int hashCode = Arrays.hashCode(userList);
        System.out.println("hashCode: " + hashCode);

        String[] userList2 = {"dazuo", "wang", "uu"};
        System.out.println(Arrays.equals(userList, userList2));
    }

    /**
     * 返回数组元素的字符串形式（一维数组和多维数组）
     */
    @Test
    void testArraysToString () {
        String[][] userList = {{"dazuo"}, {"name", "age"}, {"ok", "yes"}};
        // 返回数组元素的字符串形式
        System.out.println(Arrays.toString(userList));
        // 返回多维数组元素的字符串形式
        System.out.println(Arrays.deepToString(userList));
    }

    /**
     * 返回数组的流Stream（JDK 8新增）
     * <a href="https://www.cnblogs.com/zxf330301/p/6586750.html">使用Java 8中的Stream</a>
     */
    @Test
    void testArraysStream () {
        Integer[] originArray = {1, 2, 3, 4};
        List<Integer> list = Arrays.stream(originArray).collect(Collectors.toList());

        list.add(6);
        System.out.println("before list: " + list);

        // stream本身并不存储数据，数据是存储在对应的collection里，或者在需要的时候才生成的；
        // stream不会修改数据源，总是返回新的stream；
        originArray[0] = 2;
        System.out.println("after list: " + list);
    }
}
