package com.maxzuo.dubbo;

/**
 * Created by zfh on 2019/12/15
 */
public class ScUserServiceImpl implements IScUserService {

    @Override
    public void save(Integer id) {
        System.out.println("服务端收到调用：" + id);
    }
}
