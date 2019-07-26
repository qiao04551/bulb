package com.maxzuo.basic;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 一个Timer调度的例子 (Timer和TimerTask)
 * Created by zfh on 2019/03/01
 */
public class TimerExample {

    public static void main(String[] args) {
        reminder(3, 4);
    }

    private static Integer count = 0;

    /**
     * 执行周期性的定时任务
     * @param delay 执行任务之前的延迟
     * @param period 连续执行任务之间的时间
     */
    private static void reminder(Integer delay, Integer period) {
        // 多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行；
        // 使用ScheduledExecutorService代替Timer则没有这个问题。
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (count > 3) {
                    timer.cancel();
                }
                ++count;
                System.out.println("hello timer: " + LocalDateTime.now());

                // 终止
                if (count == 3) {
                    timer.cancel();
                }
            }
        }, delay * 1000, period * 1000);

        System.out.println("main thread");
    }
}
