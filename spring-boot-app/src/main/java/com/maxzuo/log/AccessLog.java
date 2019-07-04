package com.maxzuo.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 接口访问日志
 * <p>
 * Created by zfh on 2019/01/31
 */
@Aspect
@Component
public class AccessLog {

    private static final Logger logger = LoggerFactory.getLogger(AccessLog.class);

    @Around("execution(* com.maxzuo.controller..*(..))")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            logger.info(LocalDateTime.now().toString());
            return joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("recordLog 异常", t);
            throw t;
        }
    }
}
