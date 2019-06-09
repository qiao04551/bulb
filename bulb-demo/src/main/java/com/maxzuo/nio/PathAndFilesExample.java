package com.maxzuo.nio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * JDK7 新增Paths和Path类
 * <pre>
 *  1.Path与Paths
 *    1）java.nio.file.Path 接口代表一个平台无关的平台路径，描述了目录结构中文件的位置。
 *    2）Paths提供的get()方法用来获取Path对象。
 *  2.新增java.nio.file.Files类用于操作文件或目录的工具类，具备文件新增、复制、移动、删除文件等功能，使用起来超级方便。
 * </pre>
 * Created by zfh on 2019/01/24
 */
class PathAndFilesExample {

    @DisplayName("Paths常用方法")
    @Test
    void testPaths() {
        // 提供的 get() 方法用来获取 Path 对象：
        Path path = Paths.get("spring.png");
        System.out.println("path: " + path);
    }

    @DisplayName("Path常用方法")
    @Test
    void testPathCommonMethod() {
        Path path = Paths.get("spring.png");
        Path path2 = Paths.get("F:\\bulb\\bulb-demo\\spring.png");
        // 输出：false
        System.out.println("是否是绝对路径：" + path.isAbsolute());
        // 输出：true
        System.out.println("是否是绝对路径：" + path2.isAbsolute());
        // 输出：true
        System.out.println("判断是以xxx路径结束：" + path.endsWith("spring.png"));
        // 输出：false
        System.out.println("判断是以xxx路径结束：" + path.endsWith("png"));
        // 输出：true
        System.out.println("判断是以xxx路径开始：" + path.startsWith("spring.png"));
        // 输出：true
        System.out.println("判断是以xxx路径开始：" + path2.startsWith("F:\\"));
        // 输出：false
        System.out.println("判断是否是绝对路径：" + path.isAbsolute());
        // 输出：true
        System.out.println("判断是否是绝对路径：" + path2.isAbsolute());
        // 两者输出：spring.png
        System.out.println("文件名：" + path.getFileName());
        System.out.println("文件名：" + path2.getFileName());
        // 作为绝对路径返回调用Path对象
        Path newPath = path.toAbsolutePath();
        System.out.println("是否是绝对路径：" + newPath.isAbsolute());
        // 输出：spring.png
        System.out.println("toString: " + path.toString());
        // 输出：F:\bulb\bulb-demo\spring.png
        System.out.println("toString: " + path2.toString());
        // 输出：null
        System.out.println("对象的根路径：" + path.getRoot());
        // 输出：F:\
        System.out.println("对象的根路径：" + path2.getRoot());
        // 输出：null
        System.out.println("返回Path对象包含整个路径，不包含Path对象指定的文件路径：" + path.getParent());
        // 输出：F:\bulb\bulb-demo
        System.out.println("返回Path对象包含整个路径，不包含Path对象指定的文件路径：" + path2.getParent());
        // 输出：1
        System.out.println("返回Path根目录后面元素的数量: " + path.getNameCount());
        // 输出：3
        System.out.println("返回Path根目录后面元素的数量: " + path2.getNameCount());
        // 输出：spring.png，上面的数量是界限，超出界限会报错
        System.out.println("返回指定索引位置的路径名称：" + path.getName(0));
        // 输出：bulb，F:\bulb\bulb-demo\spring.png（bulb 对应 0，bulb-demo对应 1，spring.png 对应 2）
        System.out.println("返回指定索引位置的路径名称：" + path2.getName(0));
        // 将相对路径解析为绝对路径（相当于将path2的父路径，当成目标参数的父路径，拼接得到新的路径）
        Path resolvePath = path2.getParent().resolve("spring2.png");
        System.out.println("resolvePath: " + resolvePath);
    }

    @DisplayName("Files常用方法")
    @Test
    void testFilesCommonMethod() throws IOException {
        // 文件复制，成功后，返回拷贝的目标路径（如果文件已存在，会报错）
        Path copyPath = Files.copy(Paths.get("spring.png"), Paths.get("spring2.png"));
        System.out.println("copyPath: " + copyPath);
        // 删除文件或目录
        Files.delete(copyPath);
        // Files.deleteIfExists()

        if (Files.exists(Paths.get("spring.png"))) {
            // 创建一个文件
            Path newFilePath = Files.createFile(Paths.get("spring2.png"));
            Files.delete(newFilePath);
        }
        // 创建一个目录
        if (!Files.exists(Paths.get("demo"))) {
            Path newDirectory = Files.createDirectory(Paths.get("demo"));
            Files.delete(newDirectory);
        }
        long size = Files.size(Paths.get("spring.png"));
        System.out.println("指定文件的大小：" + size + " byte（字节）");

        // 移动文件
        Path copyFilePath = Files.copy(Paths.get("demo.txt"), Paths.get("demo2.txt"));
        Path moveFilePath = Files.move(copyFilePath, Paths.get("src/demo3.txt"));
        System.out.println("moveFilePath: " + moveFilePath);
        Files.delete(moveFilePath);
    }
}
