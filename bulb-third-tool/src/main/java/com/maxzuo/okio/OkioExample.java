package com.maxzuo.okio;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * okio IO框架
 * <p>
 * Created by zfh on 2020/08/27
 */
public class OkioExample {

    public static void main(String[] args) throws IOException {
        write();
        read();
    }

    private static void write() throws IOException {
        File file = new File("./data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        bufferedSink.writeString("this is some thing import \n", StandardCharsets.UTF_8);
        bufferedSink.close();
    }

    private static void read() throws IOException {
        File file = new File("./data.txt");
        BufferedSource bufferedSource = Okio.buffer(Okio.source(file));
        String str = bufferedSource.readByteString().string(StandardCharsets.UTF_8);
        System.out.println(str);
    }
}
