package com.maxzuo.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * 定义消息生产者
 * <pre>
 *  即生产者，只是泛指调用 Disruptor 发布事件的用户代码，Disruptor 没有定义特定接口或类型。
 * </pre>
 * Created by zfh on 2019/03/30
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 发布事件
     */
    public void send(String message) {
        /*
            通过顺序递增的序号来编号管理通过其进行交换的数据（事件），对数据(事件)的处理过程总是沿着序号逐个递增处理。一个 Sequence
            用于跟踪标识某个特定的事件处理者( RingBuffer/Consumer )的处理进度。虽然一个 AtomicLong 也可以用于标识进度，但定义 Sequence
            来负责该问题还有另一个目的，那就是防止不同的 Sequence 之间的CPU缓存伪共享(Flase Sharing)问题。
         */
        long sequence = ringBuffer.next();
        try {
            // 获取该序号对应的事件对象
            LongEvent event = ringBuffer.get(sequence);
            event.set(message);
        } finally {
            // 发布事件，必须在finally中以确保被调用
            ringBuffer.publish(sequence);
        }
    }
}
