package com.maxzuo.algorithm.sort;

import java.util.Arrays;

/**
 * 计数排序，线性的时间复杂度；与之类似的还有 桶排序。
 * <p>
 * Created by zfh on 2019/10/20
 */
public class CountingSort {

    public static void main(String[] args) {
        int[] array = {1, 10, 2, 3, 7, 3, 4, 9, 2, 8, 6, 9, 7, 8, 10, 5, 1};
        // 1.得出数列的最大值
        int maxVal = array[0];
        for (int i : array) {
            if (array[i] > maxVal) {
                maxVal = array[i];
            }
        }

        // 2.确定统计数组的长度
        int[] countArray = new int[maxVal + 1];

        // 3.遍历数组，填充统计数组
        for (int i : array) {
            countArray[array[i]]++;
        }

        // 4.遍历统计数组，输出结果
        int index = 0;
        int[] sortArray = new int[array.length];
        for (int i = 0; i < countArray.length; i++) {
            for (int j = 0; j < countArray[i]; j++) {
                sortArray[index++] = i;
            }
        }
        System.out.println(Arrays.toString(sortArray));
    }
}
