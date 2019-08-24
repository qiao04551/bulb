package com.maxzuo.jvm.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 获取当前JVM的JMX，监控JVM信息
 * Created by zfh on 2019/02/21
 */
public class InnerJmxMain {

    public static void main(String[] args) {
        getProcessID();
        printThreadInfo();
    }

    /** 获取当前进程ID */
    private static Integer getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
    }

    /** 打印线程信息 */
    private static void printThreadInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfo = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds());
        for (ThreadInfo item : threadInfo) {
            System.out.println(item.getThreadName());
        }
    }
}
