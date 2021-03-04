package com.maxzuo.guice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zfh on 2020/04/26
 */
public class CommunicatorImpl implements Communicator {

    private final AtomicInteger count = new AtomicInteger();

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending Message: " + message + " " + count.incrementAndGet());
    }
}
