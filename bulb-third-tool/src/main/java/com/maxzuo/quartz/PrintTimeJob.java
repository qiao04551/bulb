package com.maxzuo.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * Created by zfh on 2019/12/17
 */
public class PrintTimeJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("currentTime: " + LocalDateTime.now());
    }
}
