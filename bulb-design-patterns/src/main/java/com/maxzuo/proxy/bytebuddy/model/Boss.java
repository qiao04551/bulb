package com.maxzuo.proxy.bytebuddy.model;

/**
 * Created by zfh on 2019/02/13
 */
public class Boss {

    private String name;

    public Boss() {
        System.out.println("this is Boss");
    }

    public Boss(String name) {
        System.out.println("boss name: " + name);
    }

    public void talk() {
        System.out.println("I am boss");
    }
}
