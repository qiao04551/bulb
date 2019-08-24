package com.maxzuo.event;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 事件源，事件发生的地方（源头）
 * <p>
 * Created by zfh on 2019/08/04
 */
public class EventSourceObject {

    private Set<ApplicationListener<ApplicationEvent>> listener;

    public EventSourceObject () {
        listener = new HashSet<>();
    }

    /**
     * 给事件源注册监听器
     */
    public void addFinishListener (ApplicationListener<ApplicationEvent> eventListener) {
        listener.add(eventListener);
    }

    /**
     * 当事件发生时,通知注册在该事件源上的所有监听器做出相应的反应（调用回调方法）
     */
    public void notifies () {
        ApplicationEvent event;
        Iterator<ApplicationListener<ApplicationEvent>> iterator = listener.iterator();
        while (iterator.hasNext()) {
            ApplicationListener<ApplicationEvent> eventListener = iterator.next();
            eventListener.onApplicationEvent(new ApplicationEvent(this));
        }
    }
}
