package com.maxzuo.proxy.bytebuddy.model;

import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.List;

/**
 * Created by zfh on 2019/01/30
 */
public class ChangingLoggerInterceptor {
    public static List<String> log(String info, @Super MemoryDatabase zuper) {
        System.out.println("Calling database");
        try {
            // 调用父类的方法
            return zuper.load(info + "1");
        } finally {
            System.out.println("Returned from database");
        }
    }
}
