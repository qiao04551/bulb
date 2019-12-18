package com.maxzuo.testng;

import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * TestNG测试框架的使用
 * <p>
 * Created by zfh on 2019/12/18
 */
public class TestNgExample {

    /**
     * 在调用当前类的第一个测试方法之前运行，注释方法仅运行一次
     */
    @BeforeClass
    public void beforeClass () {
        System.out.println("beforeClass ...");
    }

    /**
     * 注释的方法将在属于test标签内的类的所有测试方法运行之前运行
     */
    @BeforeTest
    public void before() {
        System.out.println("test before ...");
    }

    /**
     * 注释方法将在每个测试方法之前运行
     */
    @BeforeMethod
    public void beforeMethod () {

    }

    /**
     * 预期异常测试
     */
    @Test(expectedExceptions=ArithmeticException.class)
    public void testCalculate () {
        int i = 1 / 0;
        System.out.println(i);
    }

    /**
     * 忽略测试
     */
    @Test(enabled = false)
    public void testEnable () {
        System.out.println("test enable");
    }

    /**
     * 超时测试，单位毫秒；超时抛异常
     */
    @Test(timeOut = 2000)
    public void testTimeout () {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "data")
    public Object[][] dataProvider(){
        // TODO: 提取参数的方式，可以换成SQL查询，从文件中读取，更加灵活

        return new Object[][]{
                {"name", 13},
                {"zhangsan", 12}
        };
    }

    /**
     * 参数测试
     */
    @Test(dataProvider = "data")
    public void testParameter(String name, Integer age) {
        System.out.println("name = " + name + "; age = " + age);
    }
}
