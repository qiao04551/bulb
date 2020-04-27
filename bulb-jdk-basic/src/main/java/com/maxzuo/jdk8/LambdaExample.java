package com.maxzuo.jdk8;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Lambda 表达式
 *
 * <p>Lambda是一个匿名函数，可以理解为一段可以传递的代码（将代码向数据一样进行传递）
 * 可以写出更简洁、更 灵活的代码。作为一种更紧凑的代码风格，使 Java的语言表达能力得到了提升.
 *
 * Created by zfh on 2019/11/06
 */
public class LambdaExample {

    /**
     * 基础语法
     * <pre>
     *   Java8中引入了一个新的操作符“->” 该操作符称为箭头操作符或Lambda操作符，箭头操作符将Lambda表达式拆分成两部分。
     *   左侧：Lambda表达式的参数列表；右侧：Lambda表达式中所需执行的功能，即 Lambda体。
     * </pre>
     */
    @Test
    public void testBasicSyntax () {
        // 格式一: 无参, 无返回值
        Runnable runnable = () -> System.out.println("hello Runable");
        // 格式二: 有一个参数, 无返回值
        Consumer consumer = (arg)-> System.out.println("Consumer arg = " + arg);
        // 格式三：只有一个参数时，参数的小括号可以省略
        Consumer consumer2 = arg-> System.out.println("Consumer arg = " + arg);
        // 格式四：两个以上参数，有返回值
        Comparator<Integer> comparator = (a, b) -> {
            System.out.println("hello comparator");
            return Integer.compare(a, b);
        };
        // 格式五：若Lambda体只有一条语句，return 和大括号都可以不写
        Comparator<Integer> comparator2 = (a, b) -> Integer.compare(a, b);
        // 格式六：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器可以通过上下文推断出数据类型。
        Comparator<Integer> comparator3 = (Integer a, Integer b) -> Integer.compare(a, b);
    }
}
