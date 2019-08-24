package com.maxzuo.juc;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 拒绝策略
 * Created by zfh on 2019/02/24
 */
public class RejectedHandlerExample implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        System.out.println("task rejected：" + executor.toString());
    }
}
