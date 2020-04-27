package com.maxzuo.jdk8.function;

/**
 * 断言型接口；接收T对象并返回boolean
 * <p>
 * Created by zfh on 2020/04/27
 */
@FunctionalInterface
public interface PredicateInterface {

    boolean assertEquals(Integer type);
}
