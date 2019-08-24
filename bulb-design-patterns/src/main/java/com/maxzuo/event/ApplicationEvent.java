package com.maxzuo.event;

import java.util.EventObject;

/**
 * 事件类，继承自java.util.EventObject类，封装了事件源对象及跟事件相关的信息。
 * <p>
 * Created by zfh on 2019/08/04
 */
public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 3855429782539198558L;

    private long timestamp;

    /**
     * 事件源
     */
    private Object source;

    public ApplicationEvent(Object source) {
        super(source);
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
