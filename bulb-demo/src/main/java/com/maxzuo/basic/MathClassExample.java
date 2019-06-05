package com.maxzuo.basic;

import org.junit.jupiter.api.Test;

/**
 * Java中Math函数的使用
 * <pre>
 *   博客：<a href="https://blog.csdn.net/xuexiangjys/article/details/79849888">Java中Math函数的使用</a>
 * </pre>
 * Created by zfh on 2019/06/05
 */
class MathClassExample {

    /**
     * 算术计算
     */
    @Test
    void testCalculation () {
        // 计算平方根
        double sqrt = Math.sqrt(16);
        System.out.println(sqrt);

        // 计算立方根
        double cbrt = Math.cbrt(8);
        System.out.println(cbrt);

        // 计算2的3次方
        double pow = Math.pow(2, 3);
        System.out.println(pow);

        // 计算最大值
        int max = Math.max(2, 3);
        System.out.println(max);

        // 计算最小值
        int min = Math.min(2, 3);
        System.out.println(min);

        // 取绝对值
        int abs = Math.abs(-1);
        System.out.println(abs);
    }

    /**
     * 进位
     */
    @Test
    void testCarry () {
        /**
         * ceil天花板的意思，就是逢余进一
         */
        System.out.println(Math.ceil(-10.1)); // -10.0
        System.out.println(Math.ceil(10.7)); // 11.0
        System.out.println(Math.ceil(-0.7)); // -0.0
        System.out.println(Math.ceil(0.0)); // 0.0
        System.out.println(Math.ceil(-0.0)); // -0.0
        System.out.println(Math.ceil(-1.7)); // -1.0

        System.out.println("-------------------");

        /**
         * floor地板的意思，就是逢余舍一
         */
        System.out.println(Math.floor(-10.1)); // -11.0
        System.out.println(Math.floor(10.7)); // 10.0
        System.out.println(Math.floor(-0.7)); // -1.0
        System.out.println(Math.floor(0.0)); // 0.0
        System.out.println(Math.floor(-0.0)); // -0.0

        System.out.println("-------------------");

        /**
         * rint 四舍五入，返回double值 注意.5的时候会取偶数 异常的尴尬=。=
         */
        System.out.println(Math.rint(10.1)); // 10.0
        System.out.println(Math.rint(10.7)); // 11.0
        System.out.println(Math.rint(11.5)); // 12.0
        System.out.println(Math.rint(10.5)); // 10.0
        System.out.println(Math.rint(10.51)); // 11.0
        System.out.println(Math.rint(-10.5)); // -10.0
        System.out.println(Math.rint(-11.5)); // -12.0
        System.out.println(Math.rint(-10.51)); // -11.0
        System.out.println(Math.rint(-10.6)); // -11.0
        System.out.println(Math.rint(-10.2)); // -10.0

        System.out.println("-------------------");
        /**
         * round 四舍五入，float时返回int值，double时返回long值
         */
        System.out.println(Math.round(10)); // 10
        System.out.println(Math.round(10.1)); // 10
        System.out.println(Math.round(10.7)); // 11
        System.out.println(Math.round(10.5)); // 11
        System.out.println(Math.round(10.51)); // 11
        System.out.println(Math.round(-10.5)); // -10
        System.out.println(Math.round(-10.51)); // -11
        System.out.println(Math.round(-10.6)); // -11
        System.out.println(Math.round(-10.2)); // -10
    }

    /**
     * 随机数
     */
    @Test
    void testRandom () {
        System.out.println(Math.random()); // [0, 1)的double类型的数
        System.out.println(Math.random() * 2);//[0, 2)的double类型的数
        System.out.println(Math.random() * 2 + 1);// [1, 3)的double类型的数
    }
}
