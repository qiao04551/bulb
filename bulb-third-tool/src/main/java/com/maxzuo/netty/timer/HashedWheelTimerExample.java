package com.maxzuo.netty.timer;

import io.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * HashedWheelTimer底层数据结构依然是使用DelayedQueue。加上一种叫做时间轮的算法来实现。
 * <pre>
 *   大量的调度任务如果每一个都使用自己的调度器来管理任务的生命周期的话，浪费cpu的资源并且很低效。
 *
 *   时间轮是一种高效来利用线程资源来进行批量化调度的一种调度模型。把大批量的调度任务全部都绑定到同一个的调度器上面，
 *   使用这一个调度器来进行所有任务的管理（manager），触发（trigger）以及运行（runnable）。能够高效的管理各种延时
 *   任务，周期任务，通知任务等等。
 *
 *   缺点，时间轮调度器的时间精度可能不是很高，对于精度要求特别高的调度任务可能不太适合。因为时间轮算法的精度取决于，
 *   时间段“指针”单元的最小粒度大小，比如，如果时间轮的格子是一秒跳一次，那么调度精度小于一秒的任务就无法被时间轮所
 *   调度。而且时间轮算法没有做宕机备份，因此无法再宕机之后恢复任务重新调度。
 * </pre>
 * Created by zfh on 2020/01/06
 */
public class HashedWheelTimerExample {

    private static final Logger logger = LoggerFactory.getLogger(HashedWheelTimerExample.class);

    public static void main(String[] args) throws InterruptedException {
        HashedWheelTimer timer = new HashedWheelTimer(2,//每tick一次的时间间隔, 每tick一次就会到达下一个槽位
                TimeUnit.SECONDS,
                4);//轮中的slot数

        // 指定一个TimerTask任务在指定的延迟后一次性执行
        timer.newTimeout(timeout -> {
            logger.info("run task ...");
        }, 2, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);
        timer.stop();
    }
}
