package com.maxzuo.commonpool.example2;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Apache commons-pool2 基本使用
 * <p>
 * Created by zfh on 2019/09/19
 */
public class CommonsPool2Example {

    public static void main(String[] args) {
        /*
            Apache commons-pool2默认提供了对于ObjectPool的5种实现：
            - SoftReferenceObjectPool
            - GenericObjectPool
            - ProxiedObjectPool
            - GenericKeyedObjectPool
            - ProxiedKeyedObjectPool
         */
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 最大活动对象数
        poolConfig.setMaxTotal(5);
        // 最大能够保持idel状态的对象数
        poolConfig.setMaxIdle(1);
        // 最小能够保持idel状态的对象数
        poolConfig.setMinIdle(1);
        // 从jedis连接池获取连接时，是否进行验证操作。如果赋值true，则得到的jedis实例肯定是可用的。
        poolConfig.setTestOnBorrow(true);
        // 把连接放回jedis连接池时，是否进行验证操作。如果赋值为true，则放回jedisPool的jedis实例肯定是可用的。
        poolConfig.setTestOnReturn(true);
        // 连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        poolConfig.setBlockWhenExhausted(false);

        ObjectPool<UserObject> objectPool = new GenericObjectPool<>(new PooledObjectFactoryExample(), poolConfig);
        try {
            Thread.sleep(3000);

            // 租借对象
            UserObject userObject = objectPool.borrowObject();
            System.out.println(userObject.toString());

            Thread.sleep(3000);

            // 归还对象
            objectPool.returnObject(userObject);

            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
