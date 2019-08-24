package com.maxzuo.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Runtime 类代表着Java程序的运行时环境，每个Java程序都有一个Runtime实例，该类会被自动创建
 *
 * Created by zfh on 2019/02/11
 */
class RuntimeTest {

    @Test
    void main() {
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

    @DisplayName("虚拟机关闭钩子")
    @Test
    void testAddShutdownHook() {
        /*
          钩子被调用的场景
            1.程序正常退出
            2.使用System.exit()
            3.终端使用Ctrl+C触发的中断
            4.系统关闭
            5.OutOfMemory宕机
            6.使用Kill pid命令干掉进程（注：在使用kill -9 pid时，是不会被调用的）
         */
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello hook!");
            }
        });
        Runtime.getRuntime().addShutdownHook(t);
        // 移除钩子
        Runtime.getRuntime().removeShutdownHook(t);
        System.out.println("hello test");
    }
}
