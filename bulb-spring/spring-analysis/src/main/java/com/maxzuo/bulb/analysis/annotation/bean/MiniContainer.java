package com.maxzuo.bulb.analysis.annotation.bean;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 容器
 * Created by zfh on 2019/09/19
 */
@Component
public class MiniContainer {

    /**
     * Resource注解指定了name属性，按照名称匹配；没有指定，就按照类型匹配。
     */
    @Resource(name = "userService")
    private UserService userService;

    public void init () {
        System.out.println("init container ...");
        userService.printName();
    }

    public void destroy () {
        System.out.println("destory container ...");
    }
}
