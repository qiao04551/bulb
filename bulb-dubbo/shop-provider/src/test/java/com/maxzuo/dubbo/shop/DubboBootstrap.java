package com.maxzuo.dubbo.shop;

/**
 * shop-provider启动类
 * Created by MAX_zuo on 2018/09/15
 */
public class DubboBootstrap {

    public static void main(String[] args) {
        System.out.println("shop-provider start...");

        // Main中会根据配置加载容器模块
        com.alibaba.dubbo.container.Main.main(args);
    }
}
