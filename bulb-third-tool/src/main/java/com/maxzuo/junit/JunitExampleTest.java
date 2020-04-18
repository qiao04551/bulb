package com.maxzuo.junit;

import org.junit.*;

/**
 * Junit4 使用示例
 * Created by zfh on 2019/05/07
 */
public class JunitExampleTest {

    /**
     * 用@BeforeClass注释一个公共静态void no-arg方法会导致它在类中的任何测试方法之前运行一次。
     * 超类的@BeforeClass方法将在当前类的方法之前运行，除非它们在当前类中被隐藏。
     */
    @BeforeClass
    public static void testBeforeClass () {
        System.out.println("before class ...");
    }

    /**
     * 使用@Before注释公共void方法会导致该方法在测试方法之前运行。超类的@Before方法将在当前类的方法之前运行，
     * 除非它们在当前类中被重写。没有定义其他顺序。
     */
    @Before
    public void testBefore () {
        System.out.println("before ...");
    }

    /**
     * 测试注释告诉JUnit，它所附加的公共void方法可以作为测试用例运行。要运行该方法，JUnit首先构造类的一个新实例，
     * 然后调用带注释的方法。测试抛出的任何异常都将被JUnit报告为失败。如果没有抛出异常，则假定测试已经成功。
     */
    @Test
    public void testMethodOne () {
        System.out.println("method two ...");
    }

    /**
     * 测试方法二
     */
    @Test
    public void testMethodTwo () {
        System.out.println("method two ...");
    }

    /**
     * 用@Test注释的方法，同时也用@Ignore注释的方法不会作为测试执行。另外，您可以使用@Ignore注释一个包含测试方法的类，
     * 并且不会执行任何包含测试的方法。
     */
    @Ignore("测试用例过时")
    @Test
    public void testMethodThree () {
        System.out.println("method three ... ");
    }

    /**
     * 可选地指定超时(以毫秒为单位)，如果测试方法花费的时间超过该毫秒数，则会导致测试方法失败。
     */
    @Test(timeout = 3000L)
    public void testTimeout () {
        try {
            Thread.sleep(4000);
            System.out.println("hello test timeout ...");
        } catch (InterruptedException e) {
            System.out.println("线程中断");
        }
    }

    /**
     * 期望异常测试
     * 当且仅当该方法抛出指定类的异常时，才会导致测试方法成功。
     */
    @Test(expected = RuntimeException.class)
    public void testExpectedException () {
        if (true) {
            throw new RuntimeException("NPE异常");
        }
    }

    /**
     * 用@After注释公共void方法会导致该方法在测试方法之后运行。即使Before或Test方法抛出异常，也保证所有@After方法都能运行。
     * 超类中声明的@After方法将在当前类的方法之后运行，除非它们在当前类中被覆盖。
     */
    @After
    public void testAfter () {
        System.out.println("before ...");
    }

    /**
     * 用@AfterClass注释一个公共静态void方法会导致该方法在类中的所有测试运行之后运行。即使before类方法抛出异常，也保证
     * 所有@AfterClass方法都能运行。超类中声明的@AfterClass方法将在当前类的方法之后运行，除非它们在当前类中被隐藏。
     */
    @AfterClass
    public static void testAfterClass () {
        System.out.println("after class ...");
    }
}
