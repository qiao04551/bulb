package com.maxzuo.everyday.logger;

/**
 * 日志工厂-生成日志记录器
 * Created by zfh on 2019/02/26
 */
public class LoggerFactory {

    public static Logger getLogger(Class<?> clazz) {
        return new RecordLogger(clazz.getName());
    }
}
