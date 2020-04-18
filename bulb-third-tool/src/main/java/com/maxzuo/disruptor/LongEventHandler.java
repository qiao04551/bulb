package com.maxzuo.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 定义事件消费者
 * <pre>
 *  Disruptor 定义的事件处理接口，由用户实现，用于处理事件，是 Consumer 的真正实现。
 * </pre>
 * Created by zfh on 2019/03/30
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.format("Event value: %s  threadName = %s  sequence = %d\n", event.get(), Thread.currentThread()
            .getName(), sequence);
    }
}
