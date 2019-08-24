package com.maxzuo.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.LocalDateTime;

/**
 * PipedInputStream和PipedOutputStream
 * 管道流，线程流，顾名思义就是在线程之间传输数据的流。主要用途自然就是用于线程之间通讯。
 *
 * Created by zfh on 2019/06/16
 */
public class PipedInputStreamExample {

    public static void main(String[] args) {
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();
        Sender sender = new Sender(pos);
        Reciver reciver = new Reciver(pis);
        try {
            pis.connect(pos);
            sender.start();
            reciver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送者
     */
    private static class Sender extends Thread {

        private PipedOutputStream pos;

        private Sender(PipedOutputStream pos) {
            this.pos = pos;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String message = LocalDateTime.now().toString() + " hello world!";
                    pos.write(message.getBytes());
                    Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 接收者
     */
    private static class Reciver extends Thread {

        private PipedInputStream pis;

        public Reciver (PipedInputStream pis) {
            this.pis = pis;
        }

        @Override
        public void run() {
            try {
                byte[] buf = new byte[1024];
                while (true) {
                    int count;
                    if ((count = pis.read(buf)) != -1) {
                        System.out.println("接收的数据：" + new String(buf, 0, count));
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
