package com.maxzuo.bulb.spring;

import com.maxzuo.bulb.spring.config.ConfigurationAnnotationExample;
import com.maxzuo.bulb.spring.model.Token;
import com.maxzuo.bulb.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p> @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
 *
 * Created by zfh on 2019/04/04
 */
public class AnnotationConfigApplicationContextExample {

    public static void main(String[] args) {
        /// 扫描包
        // scanPackageAndAutoRefreshContext();

        /// 基于指定类
        // giveAnnotatedClassAndAutoRefreshContext();

        /// 手动注册、刷新Context
        registerAnnotationClassAndRefreshContext();
    }

    /**
     * 创建一个AnnotationConfigApplicationContext实例，扫描指定的包路径并且自动刷新容器上下文
     */
    private static void scanPackageAndAutoRefreshContext () {
        // 扫描包路径
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.maxzuo.bulb.spring");

        Token bean = applicationContext.getBean(Token.class);
        System.out.println(bean);
    }

    /**
     * 创建一个AnnotationConfigApplicationContext实例，从给定的带注释的类派生bean定义，并自动刷新上下文。
     */
    private static void giveAnnotatedClassAndAutoRefreshContext () {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationAnnotationExample.class);
        Token bean = context.getBean(Token.class);
        System.out.println(bean);
    }

    /**
     * 创建一个AnnotationConfigApplicationContext实例
     * 注册一个或多个要处理的带注释的类
     * 调用refresh()刷新上下文，完全处理新类
     */
    private static void registerAnnotationClassAndRefreshContext () {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(UserService.class);
        context.refresh();

        UserService bean = context.getBean(UserService.class);
        System.out.println(bean);
    }
}
