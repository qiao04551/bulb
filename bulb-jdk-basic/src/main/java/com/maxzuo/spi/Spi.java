package com.maxzuo.spi;

import java.lang.annotation.*;

/**
 * 实现类排序
 * Created by zfh on 2019/11/14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Spi {

    int order();
}
