package com.maxzuo.event;

import java.util.EventListener;

/**
 * 事件监听器
 * <p>
 * Created by zfh on 2019/08/04
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理 ApplicationEvent
     */
    void onApplicationEvent(E event);
}
