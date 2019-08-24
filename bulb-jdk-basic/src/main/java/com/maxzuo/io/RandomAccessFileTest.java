package com.maxzuo.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile类的测试
 * Created by zfh on 2019/01/23
 */
class RandomAccessFileTest {

    @DisplayName("移动指针读文件")
    @Test
    void testReadFile() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("demo.txt", "rw");
        // 获取RandomAccessFile对象文件指针的位置，初始位置是0
        System.out.println("文件指针的初始位置：" + raf.getFilePointer());
        // 移动指针的位置
        raf.seek(4);
        byte[] bytes = new byte[2];
        System.out.println("读取的字节长度：" + raf.read(bytes));
        System.out.println("读取的内容：" + new String(bytes));
        raf.close();
    }

    @DisplayName("向文件尾部插入内容")
    @Test
    void testWriteFile() throws IOException {
        File file = new File("demo.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(raf.length());
        raf.write(String.valueOf("\r\nRandomAccessFile").getBytes());
        raf.close();
    }

    @DisplayName("向文件的指定位置写入内容")
    @Test
    void testWriteToSpecificPositionFile() throws IOException {
        // 如果直接指定位置，会覆盖调指定位置后面的内容
        File file = new File("demo.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(raf.length() - 10);
        raf.write(String.valueOf("\r\nRandomAccessFile2").getBytes());
        raf.close();

        // 解决方案：拷贝一个临时文件，分两次写入
    }
}
