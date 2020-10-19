package com.maxzuo.tuples;

import javafx.util.Pair;
import org.javatuples.Triplet;
import org.javatuples.Unit;

/**
 * javatuples的tuple类
 * <p>
 * Created by zfh on 2020/10/19
 */
public class TuplesExample {

    public static void main(String[] args) {
        Unit<Integer> unit = new Unit<>(10);
        Integer unitValue = unit.getValue0();
        System.out.println(unitValue);

        Pair<String, Integer> pair = new Pair<>("me", 100);
        System.out.printf("key = %s, value = %d\n", pair.getKey(), pair.getValue());

        Triplet<String, Integer, Boolean> triplet = new Triplet<>("me", 100, true);
        System.out.printf("value0 = %s, value1 = %d, value2 = %b\n", triplet.getValue0(), triplet.getValue1(), triplet.getValue2());
        /// 其它多个参数
        // Quartet、Quintet、Sextet、Septet、Octet、Ennead、Decade
    }
}
