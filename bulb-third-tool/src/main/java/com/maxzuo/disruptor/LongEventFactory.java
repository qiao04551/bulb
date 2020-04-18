package com.maxzuo.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 定义事件工厂
 * Created by zfh on 2019/03/30
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
