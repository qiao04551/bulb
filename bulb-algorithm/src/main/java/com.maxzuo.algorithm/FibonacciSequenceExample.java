package com.maxzuo.algorithm;

/**
 * 斐波那契数列
 * <pre>
 *  斐波那契数列（Fibonacci sequence），又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）
 *  以兔子繁殖为例子而引入，故又称为“兔子数列”，指的是这样一个数列：1、1、2、3、5、8、13、21、34、……在数学上，
 *  斐波纳契数列以如下被以递推的方法定义：F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)（n>=3，n∈N*）在现代物理、准晶
 *  体结构、化学等领域，斐波纳契数列都有直接的应用，
 * </pre>
 * <p>
 * Created by zfh on 2019/04/17
 */
public class FibonacciSequenceExample {

    public static void main(String[] args) {
        long value = recursiveAlgorithm(5);
        System.out.println(value);
    }

    /**
     * 递归算法，利用f(n) = f(n-1) + f(n-2)的特性来进行递归
     * 缺陷：当n比较大时递归非常慢，因为递归过程中存在很多重复计算。
     */
    private static long recursiveAlgorithm(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return recursiveAlgorithm(n - 1) + recursiveAlgorithm(n - 2);
    }
}
