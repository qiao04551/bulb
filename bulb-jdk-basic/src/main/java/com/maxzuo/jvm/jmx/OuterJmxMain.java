package com.maxzuo.jvm.jmx;

import com.sun.management.ThreadMXBean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.net.URL;
import java.util.Properties;

/**
 * 连接外部JMX，监控JVM信息
 * Created by zfh on 2019/02/20
 */
public class OuterJmxMain {

    public static void main(String[] args) {
        try {
            String pid = "1288";
            final JmxClient jmxClient = new JmxClient(pid);
            jmxClient.connect();
            // 注册JMXClient注销的钩子
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    jmxClient.disconnect();
                }
            }));

            System.err.println("-----------打印堆内存使用信息------------");
            printHeapInfo(jmxClient);

            System.err.println("------------打印线程统计信息-------------");
            printThreadsDetails(jmxClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印线程统计信息
     * @param jmxClient jmx客户端
     */
    private static void printThreadsDetails(JmxClient jmxClient) throws Exception {
        ThreadMXBean threadMXBean = jmxClient.getThreadMXBean();
        long[] allThreadIds = threadMXBean.getAllThreadIds();
        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(allThreadIds);
        int runCount = 0;
        int httpRunCount = 0;
        for (ThreadInfo item : threadInfos) {
            if (Thread.State.RUNNABLE.equals(item.getThreadState())) {
                ++runCount;
                if (item.getThreadName().startsWith("http")) {
                    ++httpRunCount;
                }
            }
        }
        System.out.println("runCount: " + runCount);
        System.out.println("httpRunCount: " + httpRunCount);
    }

    /**
     * 打印堆内存使用信息
     * @param jmxClient jmx客户端
     */
    private static void printHeapInfo(JmxClient jmxClient) throws Exception {
        MemoryPoolMXBean edenMemoryPool = jmxClient.getMemoryPoolManager().getEdenMemoryPool();
        long edenUsedBytes = edenMemoryPool.getUsage().getUsed();
        long edenMaxBytes = getMemoryPoolMaxOrCommited(edenMemoryPool);

        MemoryPoolMXBean survivorMemoryPool = jmxClient.getMemoryPoolManager().getSurvivorMemoryPool();
        long surUsedBytes = survivorMemoryPool.getUsage().getUsed();
        long surMaxBytes = getMemoryPoolMaxOrCommited(survivorMemoryPool);

        MemoryPoolMXBean oldMemoryPool = jmxClient.getMemoryPoolManager().getOldMemoryPool();
        long oldUsedBytes = oldMemoryPool.getUsage().getUsed();
        long oldMaxBytes = getMemoryPoolMaxOrCommited(oldMemoryPool);

        System.out.printf(" HEAP: %s/%s eden, %s/%s sur, %s/%s old%n", toMB(edenUsedBytes), toMB(edenMaxBytes),
            toMB(surUsedBytes), toMB(surMaxBytes), toMB(oldUsedBytes), toMB(oldMaxBytes));
    }

    /** 单位转换 */
    private static String toMB(long bytes) {
        if (bytes < 0) {
            return "n/a";
        }
        return Long.toString(bytes / 1024 / 1024) + "m";
    }

    private static long getMemoryPoolMaxOrCommited(MemoryPoolMXBean memoryPool) {
        MemoryUsage usage = memoryPool.getUsage();
        long max = usage.getMax();
        max = max < 0 ? usage.getCommitted() : max;
        return max;
    }

    /**
     * 输出日志信息
     * @param msg 字符串消息
     */
    private static void printLog(String msg) {
        try {
            URL resource = OuterJmxMain.class.getResource("/config.properties");
            Properties properties = new Properties();
            properties.load(new FileInputStream(resource.getPath()));
            String logFilePath = properties.getProperty("logFilePath");
            PrintStream printStream = new PrintStream(new FileOutputStream(logFilePath, true));
            printStream.println(msg);
            printStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
