package com.maxzuo.callback;

/**
 * 主函数
 * <p>
 * Created by zfh on 2019/11/22
 */
public class Main {

    public static void main(String[] args) {
        SimpleTask task = new SimpleTask();

        task.executer(() -> System.out.println("success"), () -> System.out.println("error"));
    }
}
