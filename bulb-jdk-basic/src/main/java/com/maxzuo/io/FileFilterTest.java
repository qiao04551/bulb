package com.maxzuo.io;

import java.io.File;
import java.io.FileFilter;

/**
 * Java文件过滤FileFilter
 * Created by zfh on 2019/04/01
 */
public class FileFilterTest {

    public static void main(String[] args) {
        String rootPath = "F:\\bulb\\bulb-agent\\target\\lib";
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
}
