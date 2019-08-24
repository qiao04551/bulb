package com.maxzuo.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * java.lang.System类的方法使用
 * <p>
 * Created by zfh on 2019/08/04
 */
class SystemMethod {

    @DisplayName("jvm默认的字符集")
    @Test
    void testCharset () {
        System.out.println(Charset.defaultCharset());
    }

    @DisplayName("返回当前时间(以毫秒为单位)")
    @Test
    void testCurrentTimeMillis() {
        System.out.println(System.currentTimeMillis());
    }

    /**
     * nanoTime的返回值本身则没有什么意义，因为它基于的时间点是随机的，甚至可能是一个未来的时间，所以返回值可能为负数。
     * 但是其精确度为纳秒，相对高了不少。
     */
    @Test
    void testNanoTime () {
        System.out.println(System.nanoTime());
    }

    @DisplayName("用来结束当前正在运行中的java虚拟机")
    @Test
    void testSystemExit () {
        // 正常退出
        System.exit(0);

        // status是非0，表示非正常退出
        System.exit(1);
    }

    /**
     * Runtime 类代表着Java程序的运行时环境，每个Java程序都有一个Runtime实例，该类会被自动创建
     */
    @Test
    void testRuntime () {
        Runtime runtime = Runtime.getRuntime();
        // 获取jvm可用的处理器核心的数量
        System.out.println(runtime.availableProcessors());

        try {
            // 执行系统命令
            runtime.exec("calc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * JVM关闭钩子
     * <pre>
     *   可以在下面几种场景中被调用：
     *     1.程序正常退出
     *     2.使用System.exit()
     *     3.终端使用Ctrl+C触发的中断
     *     4.系统关闭
     *     5.OutOfMemory宕机
     *     6.使用Kill pid命令干掉进程（注：在使用kill -9 pid时，是不会被调用的）
     * </pre>
     */
    @Test
    void testShutdownHook () {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute Hook.....");
            }
        }));
    }
}
