package com.maxzuo.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * 自定义类加载器
 * Created by zfh on 2019/01/26
 */
class CustomClassLoader extends ClassLoader {

    private static final String CLASSPATH = "F:\\bulb\\bulb-agent\\target\\classes\\";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String fileName = CLASSPATH + name.replace(".", "/") + ".class";
            FileInputStream is = new FileInputStream(fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            while ((len = is.read()) != -1) {
                bos.write(len);
            }
            byte[] bytes = bos.toByteArray();
            bos.close();
            is.close();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
