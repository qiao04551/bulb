package com.maxzuo.reflect.model;

import java.lang.annotation.*;

/**
 * 自定义Token注解
 * Created by zfh on 2019/01/26
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Token {
    String value();
}
