package com.maxzuo.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序，O(nlogn) 的时间复杂度，在分治过程中，以基准元素为中心，把其它元素移动到它的两边。
 * <pre>
 *   基准元素的选择：随机选择一个元素作为基准元素，并且让基准元素和数列首元素交换位置
 *
 *   单边循环法、双边循环法；基于递归和基于非递归（绝大数递归的逻辑，可以用栈来实现）
 * </pre>
 * <p>
 * Created by zfh on 2019/10/20
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {32, 10, 109, 5, 80, 98};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort (int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        // 得到基准元素的位置
        int pivotIndex = partition(arr, startIndex, endIndex);
        quickSort(arr, startIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, endIndex);
    }

    /**
     * 分治（单边循环法，使用了递归）
     */
    private static int partition(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int mark = startIndex;

        for (int i = startIndex + 1; i <= endIndex; i++) {
            // 如果遍历的元素小于基准元素，则首先将 mark 指针右移1位，然后将 最新遍历到的元素
            // 和 mark 指针所在位置的元素交换位置。
            if (arr[i] < pivot) {
                mark ++;
                int tmp = arr[mark];
                arr[mark] = arr[i];
                arr[i] = tmp;
            }
        }

        arr[startIndex] = arr[mark];
        arr[mark] = pivot;
        return mark;
    }
}
