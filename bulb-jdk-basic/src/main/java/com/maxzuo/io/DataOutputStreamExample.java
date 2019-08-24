package com.maxzuo.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 使用DataOutputStream和DataInputStream写入和读取数据
 * <pre>
 *  1.数据输出流（DataOutputStream）允许应用程序以可移植的方式将原始Java数据类型写入输出流。然后，应用程序可以使用
 *    数据输入流（DataInputStream）将数据读入。
 *  2.数据输入流（DataInputStream）允许应用程序以与机器无关的方式从底层输入流读取基本的Java数据类型。应用程序使用
 *    数据输出流（DataOutputStream）来编写数据，这些数据稍后可由数据输入流（DataInputStream）读取。
 * </pre>
 * Created by zfh on 2019/07/12
 */
public class DataOutputStreamExample {

    private static final Logger logger = LoggerFactory.getLogger(DataOutputStreamExample.class);

    @Test
    public void testWrite () {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("demo.txt"));
            dos.writeInt(1);
            dos.writeBoolean(true);
            dos.writeUTF("hello");
            dos.flush();
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRead () {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("demo.txt"));
            System.out.println(dis.readInt());
            System.out.println(dis.readBoolean());
            System.out.println(dis.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从指定Position的位置开始读
     */
    @Test
    public void testReadTargetPosition () {
        try {
            FileOutputStream fos = new FileOutputStream("demo.txt");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeLong(System.currentTimeMillis());
            dos.flush();

            // 记录当前偏移量
            long idx = fos.getChannel().position();

            dos.writeUTF("dazuo");
            dos.flush();

            // 读文件，指定position
            FileInputStream fis = new FileInputStream("demo.txt");
            fis.getChannel().position(idx);
            DataInputStream dis = new DataInputStream(fis);
            System.out.println(dis.readUTF());
            dis.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件
     *
     * 案例：快速检索日志文件，使用"索引"记录日志文件写入的偏移量(NIO)
     */
    @Test
    public void testIndexRecordPosition () {
        try {
            FileOutputStream fos = new FileOutputStream("demo.log");

            FileOutputStream idxFos = new FileOutputStream("demo.idx");
            DataOutputStream dos = new DataOutputStream(idxFos);
            for (int i = 0; i < 9; i++) {
                // 1.文件记录数据
                fos.write(("dazuo" + i).getBytes());
                fos.flush();

                long timestamp = System.currentTimeMillis();
                long position = fos.getChannel().position();

                // 2.索引文件：记录偏移量
                dos.writeLong(timestamp);
                dos.writeLong(position);
                dos.flush();

                logger.info("timestamp = {} position = {}", timestamp, position);
                Thread.sleep(1000);
            }
            fos.close();
            dos.close();
            idxFos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件（通过索引，快速查询指定时间戳的数据）
     *
     * 案例：快速检索日志文件，使用"索引"记录日志文件写入的偏移量(NIO)
     */
    @Test
    public void testRetrieveFileValue () {
        try {
            long offset = findOffset(1562916841202L);
            logger.info("offset：{}", offset);

            FileInputStream idxFos = new FileInputStream("demo.log");
            idxFos.getChannel().position(offset - 6);

            byte[] bytes = new byte[6];
            idxFos.read(bytes);
            System.out.println("value: " + new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检索偏移量（索引文件）
     * @param beginTime 开始时间戳
     * @return 偏移量
     */
    private long findOffset (long beginTime) {
        try {
            FileInputStream fis = new FileInputStream("demo.idx");
            DataInputStream dis = new DataInputStream(fis);

            while (dis.readLong() < beginTime) {
                // nothing to do
            }
            return dis.readLong();
        } catch (Exception e) {
            throw new RuntimeException("索引检索异常！");
        }
    }
}
