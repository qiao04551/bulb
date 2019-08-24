package com.maxzuo.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型类、泛型接口、泛型方法
 * <p>
 * Created by zfh on 2019/02/13
 */
class GenericityMain {

    @DisplayName("测试泛型类")
    @Test
    void testGenericityClass() {
        System.out.println("******************泛型类*****************");

        new GenericityOne<Integer>().methodOne(1);
        new GenericityOne<String>().methodOne("hello world");
        new GenericityOne<Boolean>().methodOne(true);
    }

    @DisplayName("测试泛型方法")
    @Test
    void testGenericityMethod () {
        System.out.println("****************泛型方法****************");
        new GenericityTwo<Integer>().methodOne(1);

        System.out.println("---------------------------------");

        new GenericityTwo<Integer>().methodTwo("name");
        Boolean aBoolean = new GenericityTwo<Integer>().methodTwoTo(true);
        System.out.println(aBoolean);

        System.out.println("--------------------------------");

        GenericityTwo.methodThree(123);
        String result = GenericityTwo.methodThreeTo("dazuo");
        System.out.println(result);
    }

    @DisplayName("泛型接口")
    @Test
    void testGenericityInterface () {
        System.out.println("****************泛型接口****************");

        new GenericityThree<Integer>() {
            @Override
            public void methodOne(Integer flag) {
                System.out.println("methodOne：" + flag);
            }
        }.methodOne(123);

        new GenericityThree<String>() {
            @Override
            public void methodOne(String flag) {
                System.out.println("methodOne：" + flag);
            }
        }.methodOne("hello");

        new GenericityThree<Boolean>() {
            @Override
            public void methodOne(Boolean flag) {
                System.out.println("methodOne：" + flag);
            }
        }.methodOne(true);
    }

    /**
     * @see
     */
    @DisplayName("<? extend T>类型语法")
    @Test
    void testGenericityMethodReturnValue () {
        GenericityTwo.methodFour("name");
        GenericityTwo.methodFour(1234);

        String name = GenericityTwo.methodFive("name");
        System.out.println(name);
    }

    @DisplayName("测试List<?>泛型")
    @Test
    void testGenericityList () {
        // 可以接受任何类型的“集合引用”赋值
        List<?> list = new ArrayList<>();

        // 一旦赋值后，不能添加任何元素
        //list.add("hello");

        // 但是可以remove和clear，并非immutable集合
        //list.remove(0);
        list.clear();

        // List<?>一般作为参数用来接收外部的集合，或者返回一个不知道具体类型的集合
        List<String> temp = new ArrayList<>();
        temp.add("name");
        temp.add("age");

        List<?> list2 = temp;
        System.out.println(list2);
        list2.remove(0);
        System.out.println(list2);
        list2.clear();
        System.out.println(list2);

        System.out.println("================ 引用传递，原集合 ==================");
        System.out.println(temp);
    }
}

/**
 * 泛型类
 */
class GenericityOne<T> {
    void methodOne(T flag) {
        System.out.println("methodOne：" + flag);
    }
}

/**
 * 泛型方法
 */
class GenericityTwo<K> {

    /**
     * 使用类上的泛型
     */
    void methodOne(K msg) {
        System.out.println("methodOne msg：" + msg);
    }

    /**
     * 实例调用；入参决定类型
     */
    <L> void methodTwo(L msg) {
        System.out.println("methodTwo: " + msg);
    }

    /**
     * 泛型返回值：入参决定类型
     */
    <L> L methodTwoTo (L msg) {
        System.out.println(msg.getClass());
        return msg;
    }

    /**
     * 静态方法不能访问类上的泛型；静态调用，入参决定类型
     */
    static <T> void methodThree(T msg) {
        System.out.println("methodThree：" + msg);
    }

    /**
     * 静态方法调用，入参决定 返回值
     */
    static <T> T methodThreeTo (T msg) {
        System.out.println(msg.getClass());
        return msg;
    }

    /**
     * <? extends T>方法的入参 只能是Comparable及Comparable子类的集合
     */
    static <T extends Comparable> void methodFour(T arg) {
        System.out.println("methodFour: " + arg.getClass().getName());
    }

    static <T extends Comparable> T methodFive(T arg) {
        System.out.println("methodFive: " + arg.getClass().getName());
        return arg;
    }
}

/**
 * 泛型接口
 */
interface GenericityThree<T> {

    void methodOne(T flag);
}
