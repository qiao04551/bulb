package com.maxzuo.nio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * NIO-通道测试
 * Created by zfh on 2019/01/23
 */
class ChannelTest {

    @DisplayName("分散读取和聚集写入")
    @Test
    void testGetChannel() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("demo.txt", "rw");
        // 1.获取通道
        FileChannel channel = raf.getChannel();

        // 2.分配指定大小的缓冲区
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(4);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(4);

        ByteBuffer[] byteBuffers = new ByteBuffer[] { byteBuffer1, byteBuffer2 };
        // 3.读取通道中的数据
        channel.read(byteBuffers);
        System.out.println("读取的内容1：" + new String(byteBuffers[0].array(), 0, byteBuffers[0].limit()));
        System.out.println("读取的内容2：" + new String(byteBuffers[1].array(), 0, byteBuffers[1].limit()));

        // 4.聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("demo2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();
        // 翻转
        byteBuffers[0].rewind();
        byteBuffers[1].rewind();
        channel2.write(byteBuffers);

        channel2.close();
        raf2.close();
        channel.close();
        raf.close();
    }

    @DisplayName("通道之间的数据传输(直接缓冲区)")
    @Test
    void testChannelDataTransport() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("spring.png"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("spring3.png"), StandardOpenOption.WRITE,
            StandardOpenOption.READ, StandardOpenOption.CREATE);

        // transferFrom：将数据从源通道传输到其他 Channel 中
        // transferTo()：将数据从源通道传输到其他 Channel 中
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    @DisplayName("使用直接缓冲区完成文件的复制(内存映射文件)")
    @Test
    void testUseMemoryMapFile() throws IOException {
        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("spring.png"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("spring2.png"), StandardOpenOption.WRITE,
            StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对缓冲区 进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }
}
