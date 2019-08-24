package com.maxzuo.nio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * JDK7中新增了try-with-resource语法，自动关闭实现 AutoCloseable 接口的资源，本质上是语法糖。
 * <p>
 * Created by zfh on 2019/01/24
 */
@DisplayName("自动资源管理")
class AutoCloseableTest {

    @DisplayName("基于通道的文件拷贝")
    @Test
    void testFileCopy() {
        try (
                FileChannel inChannel = FileChannel.open(Paths.get("spring.png"), StandardOpenOption.READ);
                FileChannel outChannel = FileChannel.open(Paths.get("spring2.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)
        ) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (inChannel.read(buf) != -1) {
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }
            // 可以没有关闭资源哦
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
