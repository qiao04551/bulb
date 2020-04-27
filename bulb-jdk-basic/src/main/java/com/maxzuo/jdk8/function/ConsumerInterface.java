package com.maxzuo.jdk8.function;

/**
 * 消费型接口，接收T对象，不返回值
 * <p>
 * Created by zfh on 2020/04/27
 */
@FunctionalInterface
public interface ConsumerInterface {

    void consume(Integer code, String msg);
}
