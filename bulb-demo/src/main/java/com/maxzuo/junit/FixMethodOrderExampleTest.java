package com.maxzuo.junit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Junit- 优先级测试
 * <pre>
 *   1. @FixMethodOrder注解的参数是 org.junit.runners.MethodSorters对象,在枚举类org.junit.runners.MethodSorters中定义了
 *     如下三种顺序类型：
 *     - MethodSorters.JVM ：，此种方式下测试方法的执行顺序是不可预测的，注意，JVM的顺序可能因运行而异
 *     - MethodSorters.DEFAULT(默认的顺序) ：默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
 *     - MethodSorters.NAME_ASCENDING ：按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
 *
 * </pre>
 * Created by zfh on 2019/05/07
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixMethodOrderExampleTest {

    @Test
    public void testA () {
        System.out.println("one ...");
    }

    @Test
    public void testB () {
        System.out.println("two ...");
    }

    @Test
    public void testC () {
        System.out.println("three ...");
    }
}
