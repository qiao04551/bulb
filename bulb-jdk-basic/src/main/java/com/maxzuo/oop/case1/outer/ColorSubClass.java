package com.maxzuo.oop.case1.outer;

import com.maxzuo.oop.case1.inner.Color;

/**
 * 颜色子类（包外）
 * Created by zfh on 2019/09/25
 */
public class ColorSubClass extends Color {

    /**
     * 继承关系，不在同一包内，可以访问 Color类的 public、protected 属性和方法
     */
    public static void main(String[] args) {
        // 父类的属性和方法
        Integer id = Color.id;
        Integer code = Color.code;
        Color.getId();
        Color.getCode();

        // 子类的属性
        Integer id2 = ColorSubClass.id;
        Integer code2 = ColorSubClass.code;
        ColorSubClass.getId();
        ColorSubClass.getCode();
    }
}
