package com.maxzuo.basic;

import org.junit.Test;

/**
 * 取模运算
 * <p>
 * Created by zfh on 2019/07/15
 */
public class ModuloOperationExample {

    /**
     * 情景一：左边小于右边，结果是左边
     */
    @Test
    public void testFirstScene () {
        System.out.println(1 % 6);
        System.out.println(1 % 3);
        System.out.println(2 % 6);
        System.out.println(2 % 5);
        System.out.println(2 % 3);
        System.out.println(3 % 4);
        System.out.println(3 % 5);
        System.out.println(-1 % 6);
        System.out.println(-2 % 5);
        System.out.println(-3 % 5);
        /*
            1
            1
            2
            2
            2
            3
            3
            -1
            -2
            -3
         */
    }

    /**
     * 场景二：左边等于右边，结果是0
     */
    @Test
    public void testSecondScene () {
        System.out.println(1 % 1);
        System.out.println(2 % 2);
        System.out.println(-3 % -3);
        /*
            0
            0
            0
         */

        // 右边操作数不能为0，发生异常
        System.out.println(0 % 0);
    }

    /**
     * 场景三：左边大于右边
     */
    @Test
    public void testThirdScene (){
        System.out.println(1 % 1);
        System.out.println(2 % 1);
        System.out.println(5 % 2);
        System.out.println(6 % 3);
        System.out.println(7 % 4);
        System.out.println(11 % 7);

        System.out.println("***********************");

        System.out.println(1 % -1);
        System.out.println(2 % -1);
        System.out.println(5 % -2);
        System.out.println(6 % -3);
        System.out.println(7 % -4);
        System.out.println(11 % -7);
        /*
            0
            0
            1
            0
            3
            4
            ***********************
            0
            0
            1
            0
            3
            4
         */
    }
}
