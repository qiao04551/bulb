package com.maxzuo.oop.case1.inner;

/**
 * 颜色类
 * <p>
 * Created by zfh on 2019/09/25
 */
public class Color {

    /**
     * public 属性
     */
    public static Integer id = 100;

    /**
     * 名称属性（私有）
     */
    private static String name = "颜色";

    /**
     * 编码属性（受保护）
     */
    protected static Integer code = 1;

    /**
     * 序号属性（默认，包内可见）
     */
    static Integer sort = 1;

    /**
     * public 方法
     */
    public static Integer getId () {
        return id;
    }

    /**
     * 私有方法
     */
    private static String getColorName () {
        return name;
    }

    /**
     * 受保护的方法
     */
    protected static Integer getCode () {
        return code;
    }

    /**
     * 默认，包内可见
     */
    static Integer getSort () {
        return sort;
    }
}
