package com.maxzuo.agent.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具类
 * Created by zfh on 2019/02/14
 */
public class FileUtils {

    /**
     * 将字节数组写入.class文件中
     * @param bytes 字节数组
     */
    public static void writeToFile(byte[] bytes) {
        File file = new File("/Users/dazuo/workplace/bulb/bulb-agent/ByteBuddy.class");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.flush();
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
