package com.maxzuo.protect;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.maxzuo.dubbo.common.exception.SentinelBlockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Rest controller层资源定义
 * <p>
 * Created by zfh on 2018/09/19
 */
@Aspect
@Component
public class SentinelRestControllerProtect {

    private static final Logger logger = LoggerFactory.getLogger(SentinelRestControllerProtect.class);

    @Around("execution(* com.maxzuo.controller..*(..))")
    public Object entrance (ProceedingJoinPoint joinPoint) throws Throwable {
        String application = System.getProperty("appname");
        Entry entry = null;
        try {
            String resourceName = getResourceName(joinPoint);
            // 调用链的入口
            ContextUtil.enter(resourceName, application);
            entry = SphU.entry(resourceName, EntryType.IN, 1, joinPoint.getArgs());
            return joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("【Sentinel保护资源】发生异常！", t);
            Tracer.trace(t);
            if (t instanceof BlockException) {
                throw new SentinelBlockException();
            }
            throw t;
        } finally {
            if (entry != null) {
                entry.exit(1, joinPoint.getArgs());
            }
            ContextUtil.exit();
        }
    }

    /**
     * 定义资源名
     */
    private String getResourceName (ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        StringBuilder resourceName = new StringBuilder();
        resourceName.append(className);
        resourceName.append(".");
        resourceName.append(methodName);
        resourceName.append("(");
        Class[] parameterTypes = signature.getParameterTypes();
        boolean isFirst = true;
        for (Class clazz : parameterTypes) {
            if (!isFirst) {
                resourceName.append(",");
            }
            resourceName.append(clazz.getName());
            isFirst = false;
        }
        resourceName.append(")");
        return resourceName.toString();
    }
}
