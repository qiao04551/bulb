package com.maxzuo.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务框架quartz的使用
 * <pre>
 *   功能点：
 *   - 持久性作业 - 就是保持调度定时的状态;
 *   - 作业管理 - 对调度作业进行有效的管理;
 *
 *   组成部分：
 *   - 调度器：Scheduler
 *   - 任务：JobDetail
 *   - 触发器：Trigger，包括SimpleTrigger和CronTrigger
 * </pre>
 * Created by zfh on 2019/12/17
 */
public class QuartzExample {

    public static void main(String[] args) {
        try {
            // simpleSchedule();
            cronTriggerSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SimpleTrigger是精准指定间隔
     * 实现在一个指定时间段内执行一次作业任务或一个时间段内多次执行作业任务
     */
    private static void simpleSchedule () throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(PrintTimeJob.class).withIdentity("job1", "group1").build();

        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")
                .startNow()
                // 指定时间段
                .startAt(new Date(System.currentTimeMillis() + 2000))
                .endAt(new Date(System.currentTimeMillis() + 8000))
                // 一秒钟执行一次
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();

        TimeUnit.SECONDS.sleep(10);

        scheduler.shutdown();
    }

    /**
     * CronTrigger是基于日历的作业调度
     *
     * 格式：[秒] [分] [小时] [日] [月] [周] [年]
     */
    private static void cronTriggerSchedule () throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(PrintTimeJob.class).withIdentity("job1", "group1").build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")
                .startNow()
                .startAt(new Date(System.currentTimeMillis() + 2000))
                .endAt(new Date(System.currentTimeMillis() + 8000))
                .withSchedule(CronScheduleBuilder.cronSchedule("1/3 * * * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
        scheduler.start();

        TimeUnit.SECONDS.sleep(10);

        scheduler.shutdown();
    }
}
