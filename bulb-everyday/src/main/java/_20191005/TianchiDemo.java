package _20191005;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Tianchi 选手代码，Thread.join()的使用
 * <p>
 * Created by zfh on 2019/10/05
 */
public class TianchiDemo implements Runnable {

    private static int sendTsNum = 10;

    private static AtomicLong l = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        Thread[] sends = new Thread[sendTsNum];

        for (int i = 0; i < sendTsNum; i++) {
            sends[i] = new Thread(new TianchiDemo());
        }
        for (int i = 0; i < sendTsNum; i++) {
            sends[i].start();
        }
        for (int i = 0; i < sendTsNum; i++) {
            sends[i].join();
        }

        System.out.println("main ");
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " : " + l.getAndIncrement());
    }
}
