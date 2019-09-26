package com.maxzuo.proxy.cglib.example;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义 MethodInterceptor
 * <p>
 * Created by zfh on 2019/09/26
 */
public class MyMethodInterceptor implements MethodInterceptor {

    /**
     * @param o             cglib生成的代理对象
     * @param method        被代理对象方法
     * @param objects       方法入参
     * @param methodProxy   代理方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("======插入前置通知======");
        methodProxy.invokeSuper(o, objects);
        System.out.println("======插入后者通知======");
        return null;
    }
}
