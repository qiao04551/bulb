package com.maxzuo.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序，O(n^2) 的时间复杂度，O（1）空间复杂度
 * <pre>
 *   1.从当前元素起，向后依次比较每一对相邻元素，若逆序则交换
 *   2.对所有元素均重复以上步骤，直至最后一个元素。
 * </pre>
 * Created by zfh on 2019/10/20
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {5, 1, 7, 3, 4, 2, 8, 6};
        for (int i = 0; i < array.length -1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                int tmp;
                if (array[j] > array[j + 1]) {
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
}
