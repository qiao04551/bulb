package com.maxzuo.bulb.im;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zfh on 2019/09/22
 */
public class ClientBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.maxzuo.bulb.im");
        applicationContext.start();
    }
}
