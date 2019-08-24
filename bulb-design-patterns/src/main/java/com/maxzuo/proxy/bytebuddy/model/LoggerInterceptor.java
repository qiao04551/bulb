package com.maxzuo.proxy.bytebuddy.model;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by zfh on 2019/01/29
 */
public class LoggerInterceptor {

    /**
     * 委托方法
     * @param obj          源实例对象的引用
     * @param allArguments 源方法的参数
     * @param zuper        代理对象，可以调用源方法
     * @param method       源方法的元信息
     * @throws Exception   抛出源方法的异常
     */
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Exception {
        // 调用目标方法
        zuper.call();
        System.out.println("源参数：" + Arrays.toString(allArguments));
        return null;
    }
}
