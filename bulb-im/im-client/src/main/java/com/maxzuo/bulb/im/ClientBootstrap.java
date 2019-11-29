package com.maxzuo.bulb.im;

import com.maxzuo.bulb.im.scanner.Scan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zfh on 2019/09/22
 */
public class ClientBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.maxzuo.bulb.im");
        applicationContext.start();

        startScanTask();
    }

    private static void startScanTask () {
        Thread thread = new Thread(new Scan());
        thread.setDaemon(true);
        thread.start();
    }
}
