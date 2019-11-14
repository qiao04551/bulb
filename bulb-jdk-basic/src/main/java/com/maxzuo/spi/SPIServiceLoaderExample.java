package com.maxzuo.spi;

import org.junit.Test;

import java.util.*;

/**
 * SPI机制
 * <p>
 * Created by zfh on 2019/02/07
 */
public class SPIServiceLoaderExample {

    /**
     * 加载所有实现
     */
    @Test
    public void testLoadAllImpl() {
        ServiceLoader<IOnlinePay> serviceLoader = ServiceLoader.load(IOnlinePay.class);
        for (IOnlinePay onlinePay : serviceLoader) {
            onlinePay.pay(99);
        }
    }

    /**
     * 通过排序，取第一个
     */
    @Test
    public void testLoadFirstImpl () {
        ServiceLoader<IOnlinePay> serviceLoader = ServiceLoader.load(IOnlinePay.class);
        List<IOnlinePay> payList = new ArrayList<>(10);
        for (IOnlinePay iOnlinePay : serviceLoader) {
            payList.add(iOnlinePay);
        }
        payList.sort((o1, o2) -> {
            Spi spi1 = o1.getClass().getAnnotation(Spi.class);
            Spi spi2 = o2.getClass().getAnnotation(Spi.class);
            int order1 = spi1 == null ? 0 : spi1.order();
            int order2 = spi2 == null ? 0 : spi2.order();
            return order1 - order2;
        });
        payList.get(0).pay(99);
    }
}
