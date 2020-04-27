package com.maxzuo.jdk8.function;

/**
 * 函数型接口；接收T对象，返回R对象（）
 * <p>
 * Created by zfh on 2020/04/27
 */
@FunctionalInterface
public interface FunctionInterface {

    String handle(Integer code);
}
