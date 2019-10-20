package com.maxzuo.algorithm.sort;

import java.util.Arrays;

/**
 * 堆排序
 * <pre>
 *   二叉堆本质上是一种完全二叉树，它分为两个类型：
 *   1.最大堆：
 *     最大堆的堆顶是整个堆中的最大元素
 *   2.最小堆
 *     最小堆的堆顶是整个堆中的最小元素
 *
 *   堆排序的步骤：
 *   1.将无序数组构建成最大二叉堆。
 *   2.循环删除栈顶元素，并将该元素移到集合尾部，调整堆产生新的堆顶。
 *     第一步，把无序数组构建成 最大二叉堆，这一步的时间复杂度是 O(n)。
 *     第二步，需要进行 n - 1 次循环。每次循环调用一次 downAdjust 方法，所以第二步的计算规模是（n-1)xlogn，时间复杂度
 *     为 O(nlogn)。
 *
 *   两个步骤是并列关系，所以整体的时间复杂度是 O(nlogn)
 * </pre>
 * <p>
 * Created by zfh on 2019/10/20
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] array = {232, 19, 80, 3, 10, 380};
        heapSort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 堆排序（升序）
     */
    private static void heapSort (int[] array) {
        // 1.把无序数组构建成最大堆
        for (int i = (array.length - 2) / 2; i >= 0; i--) {
            downAdjust(array, i, array.length);
        }
        System.out.println(Arrays.toString(array));

        // 2.循环删除堆顶元素，移到集合尾部，调整堆产生的新的栈顶
        for (int i = array.length - 1; i > 0; i--) {
            int tmp = array[i];
            array[i] = array[0];
            array[0] = tmp;
            downAdjust(array, 0, i);
        }
    }

    /**
     * "下沉" 调整最大堆
     */
    private static void downAdjust(int[] array, int parentIndex, int length) {
        int tmp = array[parentIndex];
        int childIndex = 2 * parentIndex + 1;
        while (childIndex < length) {
            // 如果有右孩子，且右孩子大于左孩子的值，则定位到右孩子
            if (childIndex + 1 < length && array[childIndex + 1] > array[childIndex]) {
                childIndex++;
            }
            // 如果父节点大于任何一个孩子的值，则直接跳出
            if (tmp >= array[childIndex]) {
                break;
            }
            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = 2 * childIndex + 1;
        }
        array[parentIndex] = tmp;
    }
}
