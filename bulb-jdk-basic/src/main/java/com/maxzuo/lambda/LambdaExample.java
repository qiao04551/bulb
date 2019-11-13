package com.maxzuo.lambda;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    /**
     * 函数式接口
     * <pre>
     *   接口中只有一个抽象方法时，称为函数式接口。可以使用注解@FunctionalInterface修饰 可以检查是否是函数式接口。
     *   Java SE 8中增加了一个新的包：java.util.function，它里面包含了常用的函数式接口。
     * </pre>
     */
    @Test
    public void testFunctionalInterface () {
        /*
            四大核心函数式接口：
              java.util.function.Consumer<T>：消费型接口；接收T对象，不返回值
              java.util.function.Supplier<T>：供给型接口；提供T对象（例如工厂），不接收值
              java.util.function.Function<T, R>：函数型接口；接收T对象，返回R对象（）
              java.util.function.Predicate<T>：断言型接口；接收T对象并返回boolean
         */
    }

    /**
     * 方法引用
     */
    @Test
    public void testMethodReference () {
        // 格式一：类名::静态方法名
        // 例如：比较器
        Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b);
        // 等同于
        Comparator<Integer> comparator2 = Integer::compare;

        // 格式二：对象名::实例方法名
        PrintStream out = System.out;
        Consumer consumer = out::println;
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
