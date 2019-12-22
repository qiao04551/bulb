package com.maxzuo.sofa;

import org.springframework.stereotype.Service;

/**
 * Created by zfh on 2019/12/15
 */
@Service("helloSyncService")
public class HelloSyncServiceImpl implements IHelloSyncService {

    @Override
    public String saySync(String msg) {
        System.out.println("收到调用：" + msg);
        return msg;
    }
}
