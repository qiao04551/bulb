package com.maxzuo.nio;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * java.nio.charset包的Charset类，以及编码/解码
 * <p>
 * Created by zfh on 2019/07/04
 */
public class CharsetExample {

    private static final Logger logger = LoggerFactory.getLogger(CharsetExample.class);

    @Test
    public void testTakeCharset() {
        Properties properties = System.getProperties();
        System.out.println(properties);
        // 返回指定字符集的字符集对象
        Charset targetCharset = Charset.forName("utf8");
        logger.info("targetCharset: " + targetCharset);

        // 返回虚拟机默认的字符集对象
        Charset defaultCharset = Charset.defaultCharset();
        logger.info("defaultCharset: " + defaultCharset);
    }

    /**
     * 编码/解码
     */
    @Test
    public void charsetEncode() {
        Charset charset = Charset.defaultCharset();
        // 此字符集是否支持编码
        charset.canEncode();

        String message = "hello world";

        // 编码
        ByteBuffer byteBuffer = charset.encode(message);
        System.out.println(byteBuffer);
        while (byteBuffer.hasRemaining()) {
            System.out.println((char) byteBuffer.get());
        }

        System.out.println("##########################");

        // 解码
        CharBuffer charBuffer = charset.decode(byteBuffer);
        while (charBuffer.hasRemaining()) {
            System.out.println(charBuffer.get());
        }
    }
}
