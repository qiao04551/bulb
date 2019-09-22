package com.maxzuo.commoncli;

import java.util.Scanner;

/**
 * JDK 命令行扫描器
 * <p>
 * Created by zfh on 2019/09/22
 */
public class ScannerExample {

    public static void main(String[] args) {
        Thread t = new Thread(new ScanTask());
        t.setName("-- scanTask thread --");
        t.start();
        System.out.println("请输入 ...");
    }
}

class ScanTask implements Runnable {

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String newLine = scanner.nextLine();
            System.out.println(newLine);
        }
    }
}
