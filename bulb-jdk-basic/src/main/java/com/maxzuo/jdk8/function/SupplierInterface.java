package com.maxzuo.jdk8.function;

/**
 * 供给型接口；提供T对象，不接收值
 * <p>
 * Created by zfh on 2020/04/27
 */
@FunctionalInterface
public interface SupplierInterface {

    String get();
}
