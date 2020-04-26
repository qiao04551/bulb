package com.maxzuo.guice;

/**
 * Created by zfh on 2020/04/26
 */
public class DefaultCommunicatorImpl implements Communicator {

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending Message: " + message);
    }
}
