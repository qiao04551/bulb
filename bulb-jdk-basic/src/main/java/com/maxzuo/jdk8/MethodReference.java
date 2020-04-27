package com.maxzuo.jdk8;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用
 * <p>方法引用是用来直接访问类或者实例的已经存在的方法或者构造方法。方法引用提供了一种引用而不执行方法的方式，它需要由兼容的函数式接口
 * 构成的目标类型上下文。计算时，方法引用会创建函数式接口的一个实例。
 * <p>
 * Created by zfh on 2020/04/27
 */
public class MethodReference {

    @Test
    public void testMethodReference() {
        // 格式一：类名::静态方法名
        // 例如：比较器
        Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b);
        // 等同于
        Comparator<Integer> comparator2 = Integer::compare;

        // 格式二：对象名::实例方法名
        Consumer consumer = System.out::println;
        consumer.accept("hello");

        // 格式三：类名:实例方法名
        BiPredicate<String, String> biPredicate = String::equals;
        System.out.println(biPredicate.test("1234", "1234"));

        // 格式四：ClassName::new
        // 示例一：无参构造器引用
        Supplier<Employee> supplier = Employee::new;
        // 每一次get都是一个全新的对象
        System.out.println(supplier.get().name);

        // 示例二：有参构造器的引用
        Function<String, Employee> function = Employee::new;
        System.out.println(function.apply("yuanbao").name);

        // 示例三：数组引用
        Function<Integer, Integer[]> function1 = Integer[]::new;
        System.out.println(function1.apply(10).length);
    }
}
