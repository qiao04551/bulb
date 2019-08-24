package com.maxzuo.io;

import java.util.Scanner;

/**
 * IDEA-Junit无法使用Scanner获取键盘输入
 * Created by zfh on 2019/01/24
 */
public class ScannerExample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println("msg：" + scanner.next());
        }
    }
}
