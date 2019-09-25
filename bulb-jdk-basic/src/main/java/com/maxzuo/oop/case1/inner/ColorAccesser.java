package com.maxzuo.oop.case1.inner;

/**
 * Color类访问器（包内）
 * <p>
 * Created by zfh on 2019/09/25
 */
public class ColorAccesser {

    /**
     * 非继承关系，同一包内，可以访问 public、protected、默认 属性和方法
     */
    public static void main(String[] args) {
        Integer id = Color.id;
        Integer code = Color.code;
        Integer sort =  Color.sort;

        Color.getId();
        Color.getCode();
        Color.getSort();
    }
}
