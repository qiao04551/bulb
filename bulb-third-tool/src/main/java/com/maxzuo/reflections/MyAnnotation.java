package com.maxzuo.reflections;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by zfh on 2020/10/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyAnnotation {
}
