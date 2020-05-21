package com.maxzuo.algorithm.shuffle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 洗牌算法
 * <pre>
 *   扩展：抽牌洗牌、Fisher_Yates算法、Knuth_Durstenfeld算法、Inside_Out算法
 * </pre>
 * Created by zfh on 2020/05/21
 */
public class ShuffleExample {

    public static void main(String[] args) {
        List<Integer> aList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Collections.shuffle(aList);
        System.out.println(aList);
    }
}
