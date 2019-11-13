package com.maxzuo.chain;

/**
 * 具体处理器.
 *
 * Created by zfh on 2019/11/13
 */
public class ConcreteHandler extends Handler {

    @Override
    public void handleRequest() {
        System.out.println(this.toString() + "处理器处理");
        // 判断是否存在下一个处理器，存在则调用下一个处理器
        if (getNextHandler() != null){
            getNextHandler().handleRequest();
        }
    }
}
