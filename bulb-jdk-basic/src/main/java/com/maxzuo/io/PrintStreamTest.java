package com.maxzuo.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

/**
 * 打印流
 * Created by zfh on 2019/02/16
 */
class PrintStreamTest {

    @DisplayName("标准输入流")
    @Test
    void testSystemIn() {
        // “标准”输入流。此流已打开并准备提供输入数据。通常，此流对应于键盘输入或者由主机环境或用户指定的另一个输入源。
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }

    @DisplayName("标准错误输出流")
    @Test
    void testSystemError() {
        // “标准”错误输出流。此流已打开并准备接受输出数据。
        // 通常，此流对应于显示器输出或者由主机环境或用户指定的另一个输出目标。按照惯例，此输出流用于显示错误消息，或者显示那些
        // 即使用户输出流（变量 out 的值）已经重定向到通常不被连续监视的某一文件或其他目标，也应该立刻引起用户注意的其他信息。
        PrintStream printStream = System.err;
        printStream.println("hello error!");
    }

    @DisplayName("标准输出流")
    @Test
    void testSystemOut() {
        // “标准”输出流。此流已打开并准备接受输出数据。通常，此流对应于显示器输出或者由主机环境或用户指定的另一个输出目标。
        PrintStream out = System.out;
        out.println("hello world!");
    }

    @DisplayName("打印流")
    @Test
    void testPrintStream() throws IOException {
        File file = new File("test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintStream ps = new PrintStream(file);
        ps.println("hello world!");
        ps.println("hello print!");
        ps.close();
    }

    @DisplayName("输出重定向")
    @Test
    void testRedirectOutputStream() throws Exception {
        PrintStream ps = new PrintStream("demo.txt");
        System.setOut(ps);

        // 输出到文件中
        System.out.println("hello redirectOutputStream");
    }

    @DisplayName("输入重定向")
    @Test
    void testRedirectInputStream() throws Exception {
        InputStream ps = new FileInputStream("demo.txt");
        System.setIn(ps);

        Scanner scanner = new Scanner(System.in);
        // 读完整个文件，就会终止
        while (scanner.hasNext()) {
            System.out.println(scanner.nextLine());
        }
    }

}
