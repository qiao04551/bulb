package com.maxzuo.thread.singleton;

import com.maxzuo.thread.annations.Recommend;
import com.maxzuo.thread.annations.ThreadSafe;
import org.junit.jupiter.api.Test;

/**
 * 单例模式-使用枚举来实例化
 * Created by zfh on 2019/02/24
 */
@ThreadSafe
@Recommend
public class SingletonExample2 {

    private SingletonExample2() {

    }

    @Test
    public static SingletonExample2 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private SingletonExample2 singleton;

        /** JVM保证这个方法绝对只会执行一次 */
        Singleton() {
            singleton = new SingletonExample2();
        }

        public SingletonExample2 getInstance() {
            return singleton;
        }
    }
}
