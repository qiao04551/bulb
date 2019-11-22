package com.maxzuo.callback;

/**
 * 具体的任务实现
 * <p>
 * Created by zfh on 2019/11/22
 */
public class SimpleTask extends Task {

    @Override
    public boolean exec() {
        System.out.println("this is SimpleTask.exec()");
        return true;
    }
}
