package com.maxzuo.thread.singleton;

import com.maxzuo.thread.annations.ThreadSafe;

/**
 * 懒汉模式 -》 volatile + 双重检测机制
 * Created by zfh on 2019/02/24
 */
@ThreadSafe
public class SingletonExample1 {

    private SingletonExample1() {

    }

    /*
        双重检测机制不是线程安全，存在指令重排：
          理想的指令执行顺序
          1.memory = allocate() 分配对象的内存空间
          2.instance = memory 设置intance指向刚分配的内存
          3.ctorInstance() 初始化对象

          实际的指令执行执行顺序
          1.memory = allocate() 分配对象的内存空间
          3.ctorInstance() 初始化对象
          2.instance = memory 设置intance指向刚分配的内存

       解决方法：使用volatile禁止指令重排
     */

    private static volatile SingletonExample1 instance = null;

    public static SingletonExample1 getInstance() {
        // 双重检测机制
        if (instance == null) {
            // 同步锁
            synchronized (SingletonExample1.class) {
                if (instance == null) {
                    instance = new SingletonExample1();
                }
            }

        }
        return instance;
    }
}
