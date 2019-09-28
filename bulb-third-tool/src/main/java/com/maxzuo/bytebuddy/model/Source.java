package com.maxzuo.bytebuddy.model;

/**
 * Created by zfh on 2019/01/29
 */
public class Source {

    public Source() {
        System.out.println("this is source");
    }

    public static void methodOne() {
        System.out.println("this is one");
    }

    public static void methodTwo(String name) {
        System.out.println("this is two");
    }

    public void methodThree(String name) {
        System.out.println("this is three");
    }
}
