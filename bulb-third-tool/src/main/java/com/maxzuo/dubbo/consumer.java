package com.maxzuo.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 消费者配置
 * Created by zfh on 2020/05/21
 */
public class consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo/dubbo-consumer.xml");
        context.start();

        TokenService tokenService = context.getBean(TokenService.class);
        String token = tokenService.getToken(10);
        System.out.println(token);
    }
}
