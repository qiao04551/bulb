package com.maxzuo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试主类
 * Created by zfh on 2019/03/25
 */
public class MainTest {

    private static final ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int a = i;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(a);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
