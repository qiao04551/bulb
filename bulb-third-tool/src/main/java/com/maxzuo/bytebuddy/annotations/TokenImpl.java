package com.maxzuo.bytebuddy.annotations;

import java.lang.annotation.Annotation;

/**
 * 注解的实现类
 * Created by zfh on 2019/02/25
 */
public class TokenImpl implements Token {
    @Override
    public Class<? extends Annotation> annotationType() {
        return Token.class;
    }
}
