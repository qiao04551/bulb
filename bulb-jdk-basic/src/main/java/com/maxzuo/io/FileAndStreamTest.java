package com.maxzuo.io;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件 和 流
 *
 * Created by zfh on 2019/01/24
 */
class FileAndStreamTest {

    /**
     * 文件路径
     */
    @Test
    void testFilePath() {
        // 1.文件的相对路径，是项目执行命令的路径：F:\bulb\bulb-demo
        new File("spring.png");
        // 2.文件的绝对路径
        new File("F:\\bulb\\bulb-demo\\spring.png");

        // 3.path以’/'开头时，则是从ClassPath根下获取；
        URL url = FileAndStreamTest.class.getResource("/");
        // 输出：/F:/bulb/bulb-demo/target/classes/，
        System.out.println("path：" + url.getPath());
        URL url2 = FileAndStreamTest.class.getResource("/spring.png");
        // 输出：/F:/bulb/bulb-demo/target/classes/spring.png
        System.out.println("path2：" + url2.getPath());

        // 3.path不以’/'开头时，默认是从此类所在的包下取资源；
        URL url3 = FileAndStreamTest.class.getResource("");
        // 输出：/F:/bulb/bulb-demo/target/classes/com/maxzuo/io/
        System.out.println("path3: " + url3.getPath());
        // 4.不存在的文件
        URL url4 = FileAndStreamTest.class.getResource("spring.png");
        // 输出：null
        System.out.println("url4: " + url4);

        // 5.找到文件直接转换为输入流
        InputStream resourceAsStream = FileAndStreamTest.class.getResourceAsStream("/spring.png");

        // 6.path不能以’/'开头时，返回NULL；path是从ClassPath根下获取；
        URL url5 = FileAndStreamTest.class.getClassLoader().getResource("spring.png");
        // 输出：/F:/bulb/bulb-demo/target/classes/spring.png
        System.out.println("path5: " + url5.getPath());
    }

    /**
     * 使用 File 类操作文件
     */
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

    /**
     * JDK7 新增Paths和Path类
     * <pre>
     *  1.Path与Paths
     *    1）java.nio.file.Path 接口代表一个平台无关的平台路径，描述了目录结构中文件的位置。
     *    2）Paths提供的get()方法用来获取Path对象。
     *  2.新增java.nio.file.Files类用于操作文件或目录的工具类，具备文件新增、复制、移动、删除文件等功能，使用起来超级方便。
     */
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

    /**
     * Files操作文件
     */
    @Test
    void testFiles () throws IOException {
        // 提供的 Paths.get() 方法用来获取 Path 对象
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

    /**
     * 文件描述符
     *
     * <pre>
     *   1.FileDescriptor 是“文件描述符”。可以被用来表示开放文件、开放套接字等。
     *
     *   2.以FileDescriptor表示文件来说：当FileDescriptor表示某文件时，我们可以通俗的将FileDescriptor看成是该文件。
     *     但是，我们不能直接通过FileDescriptor对该文件进行操作；若需要通过FileDescriptor对该文件进行操作，则需要新
     *     创建FileDescriptor对应的FileOutputStream，再对文件进行操作。
     * </pre>
     */
    @Test
    void testFileDescriptor() {
        /*
            FileDescriptor
            (01) in  -- 标准输入(键盘)的描述符
            (02) out -- 标准输出(屏幕)的描述符
            (03) err -- 标准错误输出(屏幕)的描述符
         */
        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        try {
            fos.write("hello fos".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件操作符
     */
    @Test
    void testFileDescriptorOut () {
        try {
            FileOutputStream fos1 = new FileOutputStream("demo.txt");
            // 获取文件描述符
            FileDescriptor fd = fos1.getFD();

            FileOutputStream fos2 = new FileOutputStream(fd);
            // 两个都往demo.txt文件中写入
            fos1.write("hello fos1".getBytes());
            fos2.write("hello fos2".getBytes());

            fos1.close();
            fos2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件过滤
     */
    @Test
    void testFileFilter () {
        String rootPath = "/Users/dazuo/workplace/bulb/bulb-jdk-basic/";
        File file = new File(rootPath);
        // 通过文件名过滤
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String fileName = pathname.getName();
                if (fileName.contains("junit")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        if (files != null) {
            for (File file1 : files) {
                System.out.println("fileName: " + file1.getName());
            }
        }
    }

    /**
     * 测试字符流：FileWriter和FileReader
     */
    @Test
    void testFileReaderFileWriterFile() throws IOException {
        File file = new File("demo.txt");
        FileReader fileReader = new FileReader(file);
        // 读取单个字符
        System.out.println((char) fileReader.read());
        // 读取一个字符数组
        char[] chars = new char[2];
        System.out.println("读取的字符长度：" + fileReader.read(chars));
        System.out.println("char[] = " + new String(chars));
        fileReader.close();
    }

    /**
     * 测试字节流：FileInputStream 和 FileOutputStream
     */
    @Test
    void testFileInputStreamAndOutputStream() throws IOException {
        File file = new File("demo.txt");
        FileInputStream is = new FileInputStream(file);
        System.out.println("读取一个数据字节：" + (char) is.read());
        byte[] bytes = new byte[2];
        System.out.println("读取字节数组的长度：" + is.read(bytes));
        System.out.println("读取的字符：" + new String(bytes));
        is.close();
    }

    /**
     * 字节流和字符串的转换：InputStreamReader和 OutputStreamWriter
     */
    @Test
    void convertStream() throws IOException {
        File file = new File("demo.txt");
        FileInputStream is = new FileInputStream(file);
        // 字节流转换为字符流
        InputStreamReader isr = new InputStreamReader(is);
        // 读取一个字符
        char[] chars = new char[2];
        System.out.println("读取的字符长度：" + isr.read(chars));
        System.out.println("读取的字符：" + new String(chars));
        isr.close();
        is.close();
    }
}
