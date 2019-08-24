package com.maxzuo.io;

import org.junit.jupiter.api.Test;

import java.io.FileDescriptor;
import java.io.FileOutputStream;

/**
 * FileDescriptor 是“文件描述符”。
 * FileDescriptor 可以被用来表示开放文件、开放套接字等。
 * 以FileDescriptor表示文件来说：当FileDescriptor表示某文件时，我们可以通俗的将FileDescriptor看成是该文件。但是，我们不能直接通过
 * FileDescriptor对该文件进行操作；若需要通过FileDescriptor对该文件进行操作，则需要新创建FileDescriptor对应的FileOutputStream，
 * 再对文件进行操作。
 *
 * Created by zfh on 2019/02/20
 */
class FileDescriptorTest {

    @Test
    void test1() throws Exception {
        /*
            FileDescriptor
            (01) in  -- 标准输入(键盘)的描述符
            (02) out -- 标准输出(屏幕)的描述符
            (03) err -- 标准错误输出(屏幕)的描述符
         */
        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        fos.write("hello fos".getBytes());
        fos.close();
    }

    @Test
    void test2() throws Exception {
        FileOutputStream fos1 = new FileOutputStream("demo.txt");
        // 获取文件描述符
        FileDescriptor fd = fos1.getFD();

        FileOutputStream fos2 = new FileOutputStream(fd);
        // 两个都往demo.txt文件中写入
        fos1.write("hello fos1".getBytes());
        fos2.write("hello fos2".getBytes());

        fos1.close();
        fos2.close();
    }
}
