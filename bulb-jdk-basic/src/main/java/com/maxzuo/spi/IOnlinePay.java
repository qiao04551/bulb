package com.maxzuo.spi;

/**
 * 线上支付
 * <p>
 * Created by zfh on 2019/11/14
 */
public interface IOnlinePay {

    void pay(int amount);
}
