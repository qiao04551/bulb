package com.maxzuo.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * File操作文件，JDK7下推荐使用Files工具类操作文件更高效
 * Created by zfh on 2019/01/24
 */
class FileTest {

    @DisplayName("文件路径")
    @Test
    void testFilePath() {
        // 1.文件的相对路径，是项目执行命令的路径：F:\bulb\bulb-demo
        new File("spring.png");
        // 2.文件的绝对路径
        new File("F:\\bulb\\bulb-demo\\spring.png");

        // 3.path以’/'开头时，则是从ClassPath根下获取；
        URL url = FileTest.class.getResource("/");
        // 输出：/F:/bulb/bulb-demo/target/classes/，
        System.out.println("path：" + url.getPath());
        URL url2 = FileTest.class.getResource("/spring.png");
        // 输出：/F:/bulb/bulb-demo/target/classes/spring.png
        System.out.println("path2：" + url2.getPath());

        // 3.path不以’/'开头时，默认是从此类所在的包下取资源；
        URL url3 = FileTest.class.getResource("");
        // 输出：/F:/bulb/bulb-demo/target/classes/com/maxzuo/io/
        System.out.println("path3: " + url3.getPath());
        // 4.不存在的文件
        URL url4 = FileTest.class.getResource("spring.png");
        // 输出：null
        System.out.println("url4: " + url4);

        // 5.找到文件直接转换为输入流
        InputStream resourceAsStream = FileTest.class.getResourceAsStream("/spring.png");

        // 6.path不能以’/'开头时，返回NULL；path是从ClassPath根下获取；
        URL url5 = FileTest.class.getClassLoader().getResource("spring.png");
        // 输出：/F:/bulb/bulb-demo/target/classes/spring.png
        System.out.println("path5: " + url5.getPath());
    }

    @DisplayName("测试操作文件：File")
    @Test
    void testOperatorFile() throws IOException {
        File file = new File("spring.png");
        System.out.println("文件是否存在" + file.exists());
        boolean newFile = file.createNewFile();
        System.out.println("如果文件不存在，则创建空文件：" + newFile);
        File tempFileStatus = File.createTempFile("demo", ".txt");
        System.out.println("使用给定的前缀和后缀创建临时文件: " + tempFileStatus.getPath());
        System.out.println("文件名：" + file.getName());
        System.out.println("文件可读状态：" + file.canRead());
        System.out.println("绝对路径名形式：" + file.getAbsoluteFile());
        System.out.println("绝对路径字符串：" + file.getAbsolutePath());
        System.out.println("路径名字符串：：" + file.getPath());
        System.out.println("isFile：" + file.isFile());
        System.out.println("isDirectory：" + file.isDirectory());
        System.out.println("文件的长度：" + file.length());
        boolean mkdirStatus = file.mkdir();
        System.out.println("创建抽象路径名指定的目录：" + mkdirStatus);
        boolean mkdirsStatus = file.mkdirs();
        System.out.println("创建抽象路径名指定的目录，包括所有必须但不存在的父目录：" + mkdirsStatus);
        //boolean deleteStatus = file.delete();
        //System.out.println("文件删除状态：" + deleteStatus);
    }
}
