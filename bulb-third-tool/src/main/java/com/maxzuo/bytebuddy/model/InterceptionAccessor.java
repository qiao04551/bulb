package com.maxzuo.bytebuddy.model;

/**
 * Created by zfh on 2019/01/29
 */
public interface InterceptionAccessor {

    Interceptor getInterceptor();

    void setInterceptor(Interceptor interceptor);
}
