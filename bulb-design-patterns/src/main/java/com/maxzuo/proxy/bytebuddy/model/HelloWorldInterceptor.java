package com.maxzuo.proxy.bytebuddy.model;

/**
 * Created by zfh on 2019/01/29
 */
public class HelloWorldInterceptor implements Interceptor {
    @Override
    public String doSomethingElse() {
        return "hello interceptor";
    }
}
