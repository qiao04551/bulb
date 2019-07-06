package com.maxzuo.protect;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Rest controller层接口保护
 * <p>
 * Created by zfh on 2018/09/19
 */
@Aspect
@Component
public class SentinelRestControllerProtect {

    private static final Logger logger = LoggerFactory.getLogger(SentinelRestControllerProtect.class);

    private static final ExecutorService rulesPool = Executors.newSingleThreadExecutor();

    @Around("execution(* com.maxzuo.controller..*(..))")
    public Object entrance (ProceedingJoinPoint joinPoint) throws Throwable {
        String application = System.getProperty("appname");
        Entry entry = null;
        try {
            String resourceName = getResourceName(joinPoint);
            // 调用链的入口
            ContextUtil.enter(resourceName, application);
            if (resourceWhilteListFilter(resourceName)) {
                logger.info("【Sentinel保护资源】resourceName = {}", resourceName);
                entry = SphU.entry(resourceName, EntryType.IN, 1, joinPoint.getArgs());
            }
            return joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("【Sentinel保护资源】发生异常！", t);
            Tracer.trace(t);
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

    /**
     * 白名单资源过滤（不需要保护）
     * @param resourceName 资源名
     */
    private boolean resourceWhilteListFilter (String resourceName) {
        // TODO: 通过资源名匹配，匹配成功返回false
        return true;
    }

    /**
     * 异步初始化预定义规则
     * @param resourceName 资源名
     */
    private void asyncInitResourcePredefinedRules (String resourceName) {
        rulesPool.submit(new Runnable() {
            @Override
            public void run() {
                // TODO: 检查资源是否有规则，没有，就插入新规则

            }
        });
    }

    @Around("execution(* com.maxzuo.bulb.api..*(..))")
    public Object dubboEntrance (ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
