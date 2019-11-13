package com.maxzuo.chain;

/**
 * 抽象处理器
 *
 * Created by zfh on 2019/11/13
 */
public abstract class Handler {

    private Handler nextHandler;

    public abstract void handleRequest();

    public Handler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
