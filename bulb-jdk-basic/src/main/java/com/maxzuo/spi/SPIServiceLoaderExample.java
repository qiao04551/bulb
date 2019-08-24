package com.maxzuo.spi;

import java.util.ServiceLoader;

/**
 * SPI机制
 * <p>
 * Created by zfh on 2019/02/07
 */
public class SPIServiceLoaderExample {

    public static void main(String[] args) {
        ServiceLoader<IDeveloper> serviceLoader = ServiceLoader.load(IDeveloper.class);
        for (IDeveloper developer : serviceLoader) {
            developer.say();
        }
    }
}
