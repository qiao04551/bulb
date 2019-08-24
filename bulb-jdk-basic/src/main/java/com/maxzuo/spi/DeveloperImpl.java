package com.maxzuo.spi;

/**
 * Created by zfh on 2019/02/07
 */
public class DeveloperImpl implements IDeveloper {

    @Override
    public void say() {
        System.out.println("Developer test");
    }
}
