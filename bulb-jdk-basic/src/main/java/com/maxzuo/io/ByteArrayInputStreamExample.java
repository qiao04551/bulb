package com.maxzuo.io;

import java.io.*;
import java.net.URL;

/**
 * ByteArrayInputStream的使用
 * <pre>
 *  1.要创建临时性文件的程序以及网络数据的传输、数据压缩后的传输等可以提高运行的的效率，可以不用访问磁盘。
 *  2.流的来源或目的地并不一定是文件，也可以是内存中的一块空间，例如一个字节数组。
 *    java.io.ByteArrayInputStream将一个字节数组当作流输入的来源，而java.io.ByteArrayOutputStream则可以将一个字节数组当作流输出目的地。
 *  3.ByteArrayInputStream与ByteArrayOutputStream类用于以IO流的方式来完成对字节数组的内容的读写，来支持类似内存虚拟文件或者内存映像文件的功能。
 *
 *  4.创建一个新的字节数组输出流（ByteArrayOutputStream）。缓冲区容量最初是32字节，但是如果需要，它的大小会增加。
 *
 * </pre>
 * Created by zfh on 2019/05/09
 */
public class ByteArrayInputStreamExample {

    public static void main(String[] args) {
        //stringTransport();
        fileTransport();
    }

    /**
     * 字符串的转换
     */
    private static void stringTransport () {
        String data = "hello world";
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        advancedTransform(bais, baos);

        byte[] out = baos.toByteArray();
        System.out.println(new String(out));
    }

    /**
     * 文件流的转换
     */
    private static void fileTransport () {
        /*
            1.读取文件，获取输入流
            2.将输出流转换成字节数组
            3.将字节数组中 转换成 输出流
         */
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource("spring.png");
            if (resource == null) {
                return;
            }
            // 1.获取输入流
            FileInputStream fis = new FileInputStream(new File(resource.getFile()));
            // 2.使用字节数组接收输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] carrier = new byte[1024];
            int count;
            while ((count = fis.read(carrier)) != -1) {
                baos.write(carrier, 0, count);
            }

            // 3.将字节数组输出到文件
            byte[] dataArray = baos.toByteArray();
            FileOutputStream fos = new FileOutputStream(new File("spring3.png"));
            fos.write(dataArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 流的转换
     */
    private static void transform(InputStream is, OutputStream os) {
        int ch;
        try {
            while ((ch = is.read()) != -1) {
                os.write(ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用缓冲区 进行流的转换
     */
    private static void advancedTransform (InputStream is, OutputStream os) {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        byte[] carrier = new byte[1024];
        try {
            int count;
            while ((count = bis.read(carrier)) != -1) {
                bos.write(carrier, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
