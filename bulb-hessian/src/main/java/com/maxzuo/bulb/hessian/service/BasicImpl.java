package com.maxzuo.bulb.hessian.service;

/**
 * 接口实现类
 * <p>
 * Created by zfh on 2019/08/13
 */
public class BasicImpl implements IBasic{

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
