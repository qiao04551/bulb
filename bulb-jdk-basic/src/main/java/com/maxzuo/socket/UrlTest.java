package com.maxzuo.socket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL类用于获取一个链接的 协议，主机名，资源位置。
 * Created by zfh on 2019/01/24
 */
class UrlTest {

    @DisplayName("获取URL基本信息")
    @Test
    void testUrlInfo() throws MalformedURLException {
        URL url = new URL("https://github.com/Corezuo");
        System.out.println("获取协议：" + url.getProtocol());
        System.out.println("获取主机：" + url.getHost());
        System.out.println("获取端口：" + url.getPort());
        System.out.println("获取文件路径：" + url.getPath());
        System.out.println("获取文件名：" + url.getFile());
        System.out.println("获取相对路径，就是锚点，即#号后面的内容：" + url.getRef());
        System.out.println("查询参数：" + url.getQuery());
    }

    @DisplayName("获取网页内容")
    @Test
    void getHtmlContent() throws IOException {
        URL url = new URL("https://github.com/Corezuo");
        InputStream inputStream = url.openStream();
        InputStreamReader is = new InputStreamReader(inputStream);
        FileWriter fw = new FileWriter("github.html");

        char[] chars = new char[1024];
        int charCount;
        while ((charCount = is.read(chars)) != -1) {
            fw.write(chars, 0, charCount);
        }

        fw.flush();
        fw.close();
        is.close();
        inputStream.close();
    }
}
