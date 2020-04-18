package com.maxzuo.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Junit参数化测试
 *
 * Created by zfh on 2019/05/07
 */
// Step 1：注解类
@RunWith(Parameterized.class)
public class JunitParameterizedExampleTest {

    // Step 2：定义私有成员
    private String name;

    private Integer age;

    // Step 3：定义构造方法
    public JunitParameterizedExampleTest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // Step 4：创建一个使用@Parameters注解的公共静态方法，它将需要测试的各种变量值通过集合的形式返回。
    @Parameterized.Parameters
    public static Collection<Object[]> data () {
        // 二维数组；第一维转换成列表；列表的长度，测试用例就执行多少次
        return Arrays.asList(new Object[][] {
            {"dazuo", 23},
            {"wang", 23},
            {"yu", 23}
        });
    }

    @Test
    public void testEntry () {
        System.out.format("name: %s age: %d\n", name, age);
    }
}
