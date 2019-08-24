package com.maxzuo.nio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * NIO-缓冲区测试
 * Created by zfh on 2019/01/10
 */
public class BufferTest {

    @DisplayName("测试字节缓冲区")
    @Test
    void testByteBuffer() {
        // 分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("buffer中capacity大小：" + byteBuffer.capacity());
        System.out.println("buffer的界限（limit）的位置：" + byteBuffer.limit());
        System.out.println("判断缓冲区中是否还有元素：" + byteBuffer.hasRemaining());

        // 放入数据到Buffer中
        byteBuffer.put(String.valueOf("hello world").getBytes());
        // 输出：11
        System.out.println("缓冲区的当前位置：" + byteBuffer.position());

        // 翻转这个缓冲区。将limit设置为position位置，然后position被设置为0。mark置为 -1
        byteBuffer.flip();
        // 输出：11
        System.out.println("limit 界限：" + byteBuffer.limit());
        System.out.println("缓冲区的当前位置：" + byteBuffer.position());

        // 获取buffer中的数据
        byte[] bytes = new byte[4];
        byteBuffer.get(bytes);
        System.out.println("获取的数据：" + new String(bytes, 0, bytes.length));
        System.out.println("缓冲区的当前位置：" + byteBuffer.position());

        // 对缓冲区当前位置进行标记
        byteBuffer.mark();

        // 获取buffer中的数据
        byteBuffer.get(bytes);
        System.out.println("mark后-获取的数据：" + new String(bytes, 0, bytes.length));
        System.out.println("缓冲区的当前位置：" + byteBuffer.position());

        // 将位置 position转到以前设置的 mark所在的位置
        byteBuffer.reset();
        byteBuffer.get(bytes);
        System.out.println("reset后-获取的数据：" + new String(bytes, 0, bytes.length));
        System.out.println("缓冲区的当前位置：" + byteBuffer.position());

        // 和flip()操作类似，只是不把limit设置为position位置
        byteBuffer.rewind();

        // 清空缓冲区，但是缓冲区中的数据依然存在可被读取，只是处于“被遗忘”状态
        byteBuffer.clear();
        byteBuffer.get(bytes);
        System.out.println("clear前的位置：" + byteBuffer.position());
        System.out.println("clear后-获取的数据：" + new String(bytes, 0, bytes.length));
        System.out.println("clear后的位置：" + byteBuffer.position());
        System.out.println("buffer中capacity大小：" + byteBuffer.capacity());
        System.out.println("buffer的界限（limit）的位置：" + byteBuffer.limit());
        // 输出：true
        System.out.println("判断缓冲区中是否还有元素：" + byteBuffer.hasRemaining());

        // 读取的数据上面相同
        System.out.println("clear前的位置：" + byteBuffer.position());
        System.out.println("clear后-获取的数据：" + new String(bytes, 0, bytes.length));
        System.out.println("clear后的位置：" + byteBuffer.position());

        // 将设置缓冲区界限为n，并返回一个新 limit 的缓冲区对象
        Buffer newBuffer = byteBuffer.limit(512);
        // 设置缓冲区的当前位置，并返回修改后的buffer对象
        Buffer newBuffer2 = byteBuffer.position(1);
    }

    @DisplayName("测试直接缓冲区和非直接缓冲区")
    @Test
    void compareBufferType() {
        /*
            非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
            直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
         */
        // 分配直接缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println("判断是否是直接缓冲区 ：" + byteBuffer.isDirect());
    }
}
