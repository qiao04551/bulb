package com.maxzuo.callback;

/**
 * 抽象的任务类
 * <p>
 * Created by zfh on 2019/11/22
 */
public abstract class Task {

    void executer(Callback sCallback, Callback eCallback) {
        boolean exec = exec();
        if (exec) {
            sCallback.call();
        } else {
            eCallback.call();
        }
    }

    public abstract boolean exec();
}
