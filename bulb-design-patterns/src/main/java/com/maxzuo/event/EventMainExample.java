package com.maxzuo.event;

/**
 * Java事件机制包括三个部分：事件、事件监听器、事件源。
 * <pre>
 *   设计模式-观察者模式：在对象之间定义了一对多的依赖，这样一来，当一个对象改变状态，依赖它的对象会收到通知并自动更新。
 *                     其实就是发布订阅模式，发布者发布信息，订阅者获取信息，订阅了就能收到信息，没订阅就收不到信息。
 * </pre>
 * Created by zfh on 2019/08/04
 */
public class EventMainExample {

    public static void main(String[] args) {
        EventSourceObject source = new EventSourceObject();

        // 注册事件
        source.addFinishListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("timestamp: " + event.getTimestamp());
            }
        });

        // 触发事件
        source.notifies();
    }
}
