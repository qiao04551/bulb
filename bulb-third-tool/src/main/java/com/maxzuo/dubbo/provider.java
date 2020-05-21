package com.maxzuo.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 服务提供者
 * Created by zfh on 2020/05/21
 */
public class provider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo/dubbo-provider.xml");
        context.start();

        System.in.read();
    }
}
