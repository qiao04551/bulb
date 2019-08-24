package com.maxzuo.socket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于TCP的socket通信
 * Created by zfh on 2019/01/24
 */
@DisplayName("基于TCP的socket通信")
class TestTcpSocket {

    @DisplayName("服务端")
    @Test
    void testServer() throws IOException {
        // 1.创建ServerSocket对象并监听端口
        ServerSocket serverSocket = new ServerSocket(8089);
        // 2.监听客户端请求（阻塞）
        Socket socket = serverSocket.accept();
        // 3.建立连接，读取客户端数据
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        char[] chars = new char[1024];
        StringBuilder stringBuilder = new StringBuilder();
        int charCount;
        while ((charCount = isr.read(chars)) != -1) {
            stringBuilder.append(chars, 0, charCount);
        }
        System.out.println("客户端说：" + stringBuilder.toString() + "  时间戳：" + System.currentTimeMillis());
        socket.shutdownInput();

        String msg = "你好，客户端";
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(msg);
        osw.flush();

        osw.close();
        os.close();
        isr.close();
        is.close();
        // 关闭连接
        socket.close();
        serverSocket.close();
    }

    @DisplayName("客户端")
    @Test
    void testClient() throws IOException {
        // 1.创建Socket对象
        Socket socket = new Socket("127.0.0.1", 8089);
        // 2.建立连接后，通过输出流向服务端发送请求信息
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write("你好，服务器");
        osw.flush();
        socket.shutdownOutput();

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = new char[1024];
        int charCount;
        while ((charCount = isr.read(chars)) != -1) {
            stringBuilder.append(chars, 0, charCount);
        }
        System.out.println("服务器说：" + stringBuilder.toString() + "  时间戳：" + System.currentTimeMillis());

        isr.close();
        is.close();
        osw.close();
        os.close();
        socket.close();
    }
}
