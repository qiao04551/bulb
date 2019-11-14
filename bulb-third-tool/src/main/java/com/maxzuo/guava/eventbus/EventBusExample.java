package com.maxzuo.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Guava 事件总线
 * <p>
 * Created by zfh on 2019/11/14
 */
public class EventBusExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        // 注册
        eventBus.register(new Object() {
            @Subscribe
            public void subscribe(String msg) {
                System.out.println("收到：" + msg);
            }
        });

        eventBus.register(new Object() {
            @Subscribe
            public void subscribe(String msg){
                System.out.println("收到2：" + msg);
            }
        });

        // 发送
        eventBus.post("this is message");
    }
}
