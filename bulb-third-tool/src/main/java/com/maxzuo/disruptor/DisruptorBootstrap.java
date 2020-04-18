package com.maxzuo.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.maxzuo.juc.NamedThreadFactory;

/**
 * 启动disruptor
 * <p>
 * Created by zfh on 2019/03/30
 */
public class DisruptorBootstrap {

    public static void main(String[] args) {
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
        NamedThreadFactory threadFactory = new NamedThreadFactory("一号机房");
        int ringBufferSize = 1024 * 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(eventFactory, ringBufferSize, threadFactory);

        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();

        // 缓冲区：负责对通过 Disruptor 进行交换的数据（事件）进行存储和更新。
        // 在一些更高级的应用场景中，Ring Buffer 可以由用户的自定义实现来完全替代。
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // 发布事件
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        for (int i = 0; i < 100000; i++) {
            producer.send("hello dazuo" + i);
        }

        // 关闭disruptor，方法会堵塞，直到所有的事件都得到处理
        disruptor.shutdown();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
