package com.maxzuo.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Java位逻辑运算符（进制转换）
 * <p>
 * Created by zfh on 2019/08/07
 */
class BitwiseLogicalOperatorExample {

    @DisplayName("二进制转换二进制")
    @Test
    void testDecimalToBinary () {
        /*
           将10进制的数 8 转换为 2进制，计算过程如下：
                 |
               2 | 8      余数
                 |___
                 |
               2 | 4       0
                 |___
                 |
               2 | 2       0
                 |___
                 |
               2 | 1       0
                 |___

                   0       1

            结果：1000
         */
        String s = Integer.toBinaryString(8);
        System.out.println(s);
    }

    @DisplayName("二进制转10进制")
    @Test
    void testBinaryToDecimal () {
        /*
            将二进制数 1000 转换为 十进制，计算过程如下：

                       1                    0                    0                    0
                       |                    |                    |                    |
                       |                    |                    |                    |
                       |                    |                    |                    |
                       |                    |                    |                    |
               1 * Math.pow(2, 3) + 0 * Math.pow(2, 2) + 0 * Math.pow(2, 1) + 0 * Math.pow(2, 0)
         */
        double value = 1 * Math.pow(2, 3) + 0 * Math.pow(2, 2) + 0 * Math.pow(2, 1) + 0 * Math.pow(2, 0);
        System.out.println((int)value);
    }

    @DisplayName("原码、反码、补码")
    @Test
    void testOriginalCode () {
        /*
           一、机器数和真值
              1.机器数
                一个数在计算机中的二进制表示形式,  叫做这个数的机器数。机器数是带符号的，在计算机用一个数的最高位存放符号, 正数为0, 负数为1.
                比如，十进制中的数 +3 ，计算机字长为8位，转换成二进制就是00000011。如果是 -3 ，就是 10000011 。
                那么，这里的 00000011 和 10000011 就是机器数。
              2.真值
                因为第一位是符号位，所以机器数的形式值就不等于真正的数值。例如上面的有符号数 10000011，其最高位1代表负，其真正数值是 -3
                而不是形式值131（10000011转换成十进制等于131）。所以，为区别起见，将带符号位的机器数对应的真正数值称为机器数的真值。

                例：0000 0001的真值 = +000 0001 = +1，1000 0001的真值 = –000 0001 = –1

           二、原码, 反码, 补码的基础概念和计算方法.
              对于一个数, 计算机要使用一定的编码方式进行存储. 原码, 反码, 补码是机器存储一个具体数字的编码方式.
              1.原码
                用第一位表示符号，其余位表示值。因为第一位是符号位，所以8位二进制数的取值范围就是：[1111_1111 , 0111_1111]
                即 [-127 , 127] ,原码是容易被人脑所理解的表达方式。（ 0-表示正数 1-表示负数）
              2.反码
                正数的补码反码是其本身，负数的反码是符号位保持不变，其余位取反。例如正数1的原码是[0000_0001]，它的反码是是其本身
                [0000_0001],-1的原码是[1000_0001],其反码是[1111_1110]
              3.补码：
                正数的补码是其本身，负数的补码是在其反码的基础上+1，例如正数1的原码是[0000_0001],他的补码是其本身[0000_0001],
                -1的补码是[1111_1111]

           三、为何要使用原码, 反码和补码
              首先, 因为人脑可以知道第一位是符号位, 在计算的时候我们会根据符号位, 选择对真值区域的加减. (真值的概念在本文最开头).
              但是对于计算机, 加减乘除已经是最基础的运算, 要设计的尽量简单. 计算机辨别"符号位"显然会让计算机的基础电路设计变得十分复杂!
              于是人们想出了将符号位也参与运算的方法. 我们知道, 根据运算法则减去一个正数等于加上一个负数, 即: 1-1 = 1 + (-1) = 0 ,
              所以机器可以只有加法而没有减法, 这样计算机运算的设计就更简单了.

              于是人们开始探索 将符号位参与运算, 并且只保留加法的方法. 首先来看原码:
              计算十进制的表达式: 1 - 1 = 0
              1 - 1 = 1 + (-1) = [00000001]原 + [10000001]原 = [10000010]原 = -2
              如果用原码表示, 让符号位也参与计算, 显然对于减法来说, 结果是不正确的.这也就是为何计算机内部不使用原码表示一个数.

              为了解决原码做减法的问题, 出现了反码:
              计算十进制的表达式: 1 - 1 = 0
              1 - 1 = 1 + (-1) = [0000 0001]原 + [1000 0001]原 = [0000 0001]反 + [1111 1110]反 = [1111 1111]反 = [1000 0000]原 = -0
              发现用反码计算减法, 结果的真值部分是正确的. 而唯一的问题其实就出现在"0"这个特殊的数值上. 虽然人们理解上 +0 和 -0 是一样的,
              但是 0 带符号是没有任何意义的，而且会有 [0000 0000]原 和 [1000 0000]原 两个编码表示0.

              于是补码的出现, 解决了0的符号以及两个编码的问题:
              1 - 1 = 1 + (-1) = [0000 0001]原 + [1000 0001]原 = [0000 0001]补 + [1111 1111]补 = [0000 0000]补 = [0000 0000]原
              这样 0 用 [0000 0000] 表示, 而以前出现问题的 -0 则不存在了.（高位截断）

              而且可以用 [1000 0000] 表示 -128:
              (-1) + (-127) = [1000 0001]原 + [1111 1111]原 = [1111 1111]补 + [1000 0001]补 = [1000 0000]补 = [0000 0000]原
              (-1) + (-127) 的结果应该是-128, 在用补码运算的结果中, [1000 0000]补 就是 -128. 但是注意因为实际上是使用以前的 -0 的补码来表示-128,
              所以 -128 并没有原码和反码表示.(对-128的补码表示 [1000 0000]补 算出来的原码是[0000 0000]原, 这是不正确的)

              使用补码, 不仅仅修复了0的符号以及存在两个编码的问题, 而且还能够多表示一个最低数. 这就是为什么8位二进制, 使用原码或反码
              表示的范围为[-127, +127], 而使用补码表示的范围为[-128, 127].

              因为机器使用补码, 所以对于编程中常用到的32位int类型, 可以表示范围是: [-231, 231-1] 因为第一位表示的是符号位.而使用
              补码表示时又可以多保存一个最小值.

           四、参考文档：https://www.cnblogs.com/zhangziqiu/archive/2011/03/30/ComputerCode.html
         */

        /*
            ** 计算机中所有的数据的运算都是采用补码进行，所有数据展示，采用原码 **

            byte类型溢出

            由于 127 和 3 默认是int类型的，java中使用4个字节存储int数据类型

            127对应的反码为: 0000 0000 0000 0000 0000 0000 0111 1111
            3对应的反码为:   0000 0000 0000 0000 0000 0000 0000 0011

            求其补码：
            127对应的补码为: 0000 0000 0000 0000 0000 0000 0111 1111
            3对应的补码为:   0000 0000 0000 0000 0000 0000 0000 0011

            补码进行运算：
            求和后为:       0000 0000 0000 0000 0000 0000 1000 0010（补）

            然后对求和结果进行强制类型转换，去掉高位3字节，保留地位1个字节为：1000 0010

            要想展示这个数据，需要求其原码。先求补码（1000 0010）的反码（1111 1101），再+1，得到原码为：1111 1110。（补码的补码等于原码）
         */
        byte a = 127;
        byte b = (byte) (a + 1);
        System.out.println(b);
    }

    @Test
    void test () {
        System.out.println(Integer.toBinaryString((byte)-1));
    }

    @DisplayName("位与运算符")
    @Test
    void testBitsAndOperator () {
        /*
             位与运算符为 &，其运算规则是：参与运算的数字，低位对齐，高位不足的补零，如果对应的二进制位同时为 1，那么计算结果才为 1，
             否则为 0。因此，任何数与 0 进行按位与运算，其结果都为 0。

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1    -> 11
              &
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9
                ___________

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9
         */
        System.out.println(11 & 9);

        /*
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1    -> -11
                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 0 1    -> -11 的补码
              &
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9
                ___________

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1    -> 位与运算结果（补码），求其原码
         */
        System.out.println(-11 & 9);
    }

    @DisplayName("位或运算符")
    @Test
    void testBitOrOperator () {
        /*
            位或运算符为 |，其运算规则是：参与运算的数字，低位对齐，高位不足的补零。如果对应的二进制位只要有一个为 1，那么结果就为 1；
            如果对应的二进制位都为 0，结果才为 0。

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1    -> 11
              |
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9
                _______________________________________________________________

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1    -> 11
         */
        System.out.println(11 | 9);

        /*
                1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1    -> -11
                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 0 1    -> -11 的补码
              |
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9
                _______________________________________________________________

                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1    -> 位或运算结果（补码）
                1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1    -> 求其原码
         */
        System.out.println(-11 | 9);
    }

    @DisplayName("位异或运算符")
    @Test
    void testBitXorOperator () {
        /*
            位异或运算符为 ^，其运算规则是：参与运算的数字，低位对齐，高位不足的补零，如果对应的二进制位相同（同时为 0 或同时为 1）时，
            结果为 0；如果对应的二进制位不相同，结果则为 1。

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1    -> 11
              ^
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9
                _______________________________________________________________

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0    -> 2
         */
        System.out.println(11 ^ 9);

        /*
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1    -> -11
                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 0 1    -> -11 的补码
              ^
                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9
                _______________________________________________________________

                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0    -> 位异或运算结果（补码）
                1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0    -> 求其原码
         */
        System.out.println(-11 ^ 9);
    }

    @DisplayName("位取反运算符")
    @Test
    void testBitNegationOperator () {
        /*
              位取反运算符为 ~，将内存中的补码按位取反（包括符号位）其运算规则是：只对一个操作数进行运算，将操作数二进制中的 1 改为 0，0 改为 1。

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1    -> 9 (补码等于其本身）
                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 0    -> 将补码 按位取反

                按位取反后的补码，展示实际值需要先转换为原码

                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 0 1    -> -1 得到 反码
                1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0    -> 原码 = 反码符号位不变，其余取反

                原码计算值：-10
         */
        System.out.println(~9);

        /*
                1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1     -> -9
                1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1     -> -9 的补码

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0     -> 将补码 按位取反

                按位取反后的补码，展示实际值需要先转换为原码

                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0     -> 原码

                原码计算值：8
         */
        System.out.println(~-9);
    }

    @DisplayName("左位移运算符")
    @Test
    void testLeftBitMoveOperator () {
        /*
            左移位运算符为 <<，其运算规则是：按二进制形式把所有的数字向左移动对应的位数，高位移出（舍弃），低位的空位补零。

                 移位前    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1

                 移位后  0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0
                        |                                                               |
                        |                                                               |
                       舍弃                      左移1位计算过程                          补0
         */
        System.out.println(9 << 1);

        /*
                移位前     1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1

               求其补码    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1

               补码移位  1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 0
                        |                                                               |
                        |                                                               |
                       舍弃                      左移1位计算过程                          补0

                 移位结果  1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 0
                 求其反码  1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 0 1
                 求其原码  1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0
         */
        System.out.println(-9 << 1);
    }

    @DisplayName("右位移运算符")
    @Test
    void testRightBigMoveOperator () {
        /*
            右位移运算符为 >>，其运算规则是：按二进制形式把所有的数字向右移动对应的位数，低位移出（舍弃），如果最高位是0就补0，如果最高位是1就补1。

                移位前     0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1

                移位后     0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1
                          |                                                               |
                          |                                                               |
                         补0                   右移动1位计算过程                           舍弃
        */
        System.out.println(9 >> 1);

        /*

                移位前     1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1

                取反码     1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 0

                取补码     1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1

                移位后     1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1
                          |                                                               |
                          |                                                               |
                         补1                   右移动1位计算过程                           舍弃

               取反码      1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 0

               求原码      1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1
         */
        System.out.println(-9 >> 1);

        /*
            无符号右移动，在移动位的时候与右移运算符的移动方式一样的，区别只在于补位的时候不管是0还是1，都补0（只对32位和64位有意义）

                移位前     1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1

                取反码     1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 0

                取补码     1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1

                移位后     0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1
                          |                                                               |
                          |                                                               |
                         补0                   右移动1位计算过程                           舍弃
         */
        System.out.println(-9 >>> 1);

        System.out.println(Integer.MAX_VALUE - 4);
    }

    /**
     * 位运算缩写语法
     */
    @Test
    void testAbbreviationBigOperator () {
        int a = 8;
        // 拆开就是：a = a << 1;
        a <<= 1;
        System.out.println(a);

        a >>=1;
        System.out.println(a);

        a |= 1;
        // 拆开就是：a = a | 1;
        System.out.println(a);
    }
}
