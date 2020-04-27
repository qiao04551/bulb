package com.maxzuo.jdk8.function;

import org.junit.Test;

/**
 * 函数式接口
 * <p>1.接口上加了<code>@FunctionalInterface</code>注解定义为函数式接口。<code>@FunctionalInterface</code>标识为一个函数式接口
 * 只能用在只有一个抽象方法的接口上。<code>@FunctionalInterface</code>注解不是必须的，如果该接口只有一个抽象方法可以不写，它默认就符合
 * 函数式接口，但建议都写上该注解，编译器会检查该接口是否符合函数式接口的规范。
 *
 * Created by zfh on 2020/04/27
 */
public class FunctionalInterfaceExample {

    @Test
    public void testRunnable() {
        Thread t1 = new Thread(() -> System.out.println("hello"));
        t1.start();
    }

    @Test
    public void testConsumerInterface() {
        ConsumerInterface inst = (x, y) -> {
            System.out.println("x = " + x);
            System.out.println("y = " + y);
        };
        inst.consume(10001, "ok");
    }

    @Test
    public void testSupplierInterface() {
        SupplierInterface inst = () -> "hello";
        System.out.println(inst.get());
    }

    @Test
    public void testPredicateInterface() {
        PredicateInterface inst = (x) -> x == 0;
        System.out.println(inst.assertEquals(1));
    }

    @Test
    public void testFunctionInterface() {
        FunctionInterface inst = (x) -> "hello";
        System.out.println(inst.handle(10001));
    }
}
