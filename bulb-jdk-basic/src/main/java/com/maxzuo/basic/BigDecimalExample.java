package com.maxzuo.basic;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * BigDecimal类的使用
 * <p>
 * Created by zfh on 2019/06/05
 */
class BigDecimalExample {

    /**
     * 舍入的模式
     * 博客：<a href="https://my.oschina.net/sunchp/blog/670909">BigDecimal 舍入模式（Rounding mode）介绍</a>
     */
    @Test
    void testRoundingMode () {
        /*
            BigDecimal枚举常量用法摘要

            CEILING     向正无限大方向舍入的舍入模式。
            DOWN        向零方向舍入的舍入模式。
            FLOOR       向负无限大方向舍入的舍入模式。
            HALF_DOWN   向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向下舍入。
            HALF_EVEN   向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
            HALF_UP     向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向上舍入。
            UNNECESSARY 用于断言请求的操作具有精确结果的舍入模式，因此不需要舍入。
            UP          远离零方向舍入的舍入模式
         */
        String price = "2.324";
        // 四舍五入
        System.out.println(new BigDecimal(price).setScale(2, RoundingMode.HALF_UP));
        // 同上等效
        System.out.println(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 高精度计算
     */
    @Test
    void testBigDecimalCalculation() {
        // 进行高精度计算避免结果的不可预知性，构造器一律使用new BigDecimal(String val);
        BigDecimal var1 = new BigDecimal("2.31");
        BigDecimal var2 = new BigDecimal("4.12");

        // 加、减、乘
        BigDecimal total = var1.add(var2).subtract(var2).multiply(var2);
        System.out.println("total: " + total);
        // 除，设置舍入的模式
        total = total.divide(var2, BigDecimal.ROUND_UP);

        // 设置小数位数
        System.out.println("total: " + total.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * 比较大小
     */
    @Test
    void testBigDecimalCompareTo () {
        // BigDecimal比较数字大小，如果使用equals() 1.0 和 1.00 是不一样的，可以使用compareTo进行比较：
        BigDecimal var1 = new BigDecimal("1.0");
        BigDecimal var2 = new BigDecimal("1.00");
        // -1 小于、0 等于、1 大于
        System.out.println(var1.compareTo(var2));

        // 不能使用equals比较大小
        System.out.println(var1.equals(var2));
    }

    /**
     * 使用DecimalFormat类进行数字格式化
     * <a href="https://www.cnblogs.com/farewell-farewell/p/5964349.html">数字格式化</a>
     */
    @Test
    void testBigDecimalFormat () {
        DecimalFormat dt = new DecimalFormat("###,###.###");
        String format = dt.format(111222.34567);
        System.out.println(format);
    }
}
