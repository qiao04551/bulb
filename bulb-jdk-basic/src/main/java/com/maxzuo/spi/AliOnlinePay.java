package com.maxzuo.spi;

/**
 * 支付宝支付
 * Created by zfh on 2019/11/14
 */
@Spi(order = 2)
public class AliOnlinePay implements IOnlinePay{

    @Override
    public void pay(int amount) {
        System.out.println("支付宝支付：" + amount + "元");
    }
}
