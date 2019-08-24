package com.maxzuo.proxy.jdkproxy;

/**
 * 真实的委托类
 * <p>
 * Created by zfh on 2019/04/03
 */
public class RealSubject implements Subject {

    @Override
    public void visit() {
        System.out.println("hello RealSubject");
    }

    @Override
    public String getSubject() {
        return "realSubject";
    }
}
