package com.maxzuo.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile类的测试
 * <pre>
 *   1.RandomAccessFile是java Io体系中功能最丰富的文件内容访问类。即可以读取文件内容，也可以向文件中写入内容。
 *     但是和其他输入/输入流不同的是，程序可以直接跳到文件的任意位置来读写数据。因为RandomAccessFile可以自由访
 *     问文件的任意位置，所以如果我们希望只访问文件的部分内容，而不是把文件从头读到尾，使用RandomAccessFile将会
 *     带来更简洁的代码以及更好的性能。
 *
 *   2.RandomAccessFile类包含了一个记录指针，用以标识当前读写处的位置，当程序新创建一个RandomAccessFile对象时，
 *     该对象的文件记录指针位于文件头（也就是0处），当读/写了n个字节后，文件记录指针将会向后移动n个字节。除此之外，
 *     RandomAccessFile可以自由的移动记录指针，即可以向前移动，也可以向后移动。RandomAccessFile包含了以下两个
 *     方法来操作文件的记录指针：
 * 	     long getFilePointer(); 返回文件记录指针的当前位置
 * 	     void seek(long pos); 将文件记录指针定位到pos位置
 *
 *   3.RandomAccessFile有两个构造器，一个参数用于指定文件路径，另一个参数mod指定文件的访问模式：
 *       r  ：以只读的方式打开。调用结果对象的任何write方法都将抛出IOException。
 *       rw ：打开以便读取和写入。如果该文件尚不存在，则尝试创建改文件。
 *       rws：打开以便读取和写入，对于“rw”，还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备。
 *       rwd：打开以便读取和写入，对于“rw”，还要求对文件的每个更新都同步写入到底层存储设备。
 * </pre>
 *
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
