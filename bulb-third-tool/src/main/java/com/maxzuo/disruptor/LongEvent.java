package com.maxzuo.disruptor;

/**
 * 定义事件
 * <pre>
 *  在 Disruptor 的语义中，生产者和消费者之间进行交换的数据被称为事件(Event)。
 * </pre>
 * Created by zfh on 2019/03/30
 */
public class LongEvent {

    private String message;

    public void set(String value) {
        this.message = value;
    }

    public String get() {
        return message;
    }

    @Override
    public String toString() {
        return "LongEvent{" + "message='" + message + '\'' + '}';
    }
}
