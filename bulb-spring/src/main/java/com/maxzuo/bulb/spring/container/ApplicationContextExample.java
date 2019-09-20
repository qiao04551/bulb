package com.maxzuo.bulb.spring.container;

import com.maxzuo.bulb.spring.container.bean.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 创建IOC容器的几种方式
 * <p>
 * Created by zfh on 2019/04/04
 */
public class ApplicationContextExample {

    public static void main(String[] args) {
        classPathXmlApplicationContext();
    }

    /**
     * 从类路径下的一个或多个xml配置文件中加载上下文定义，适用于xml配置的方式
     */
    private static void classPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        context.start();
        UserService bean = context.getBean(UserService.class);
        System.out.println(bean);
    }

    /**
     * 从一个或多个基于java的配置类（@Configuration）中加载上下文定义，适用于java注解的方式，扫描指定的包路径并且自动刷新容器上下文
     */
    private static void scanPackageAndAutoRefreshContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.maxzuo.bulb.spring");

        UserService bean = applicationContext.getBean(UserService.class);
        System.out.println(bean);
    }

    /**
     * 创建一个AnnotationConfigApplicationContext实例，从给定的带注释的类派生bean定义，并自动刷新上下文。
     * <pre>
     *  - @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
     * </pre>
     */
    private static void giveAnnotatedClassAndAutoRefreshContext() {
        // AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationAnnotationExample.class);
        // UserService bean = context.getBean(UserService.class);
        // System.out.println(bean);
    }

    /**
     * 创建一个AnnotationConfigApplicationContext实例
     * 注册一个或多个要处理的带注释的类
     * 调用refresh()刷新上下文，完全处理新类
     */
    private static void registerAnnotationClassAndRefreshContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(UserService.class);
        context.refresh();

        UserService bean = context.getBean(UserService.class);
        System.out.println(bean);
    }
}
