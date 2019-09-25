package com.maxzuo.oop.case1.inner;

/**
 * 颜色子类（包内）
 * Created by zfh on 2019/09/25
 */
public class ColorSubClass extends Color {

    /**
     * 继承关系，同一包内，可以访问 public、protected、默认 属性和方法
     */
    public static void main(String[] args) {
        // 父类属性
        Integer id = Color.id;
        Integer code = Color.code;
        Integer sort = Color.sort;

        // 父类方法
        Color.getId();
        Color.getCode();
        Color.getSort();

        // 子类的属性和方法
        Integer id2 = ColorSubClass.id;
        Integer code2 = ColorSubClass.code;
        Integer sort2 = ColorSubClass.sort;
        ColorSubClass.getId();
        ColorSubClass.getCode();
        ColorSubClass.getSort();
    }
}
