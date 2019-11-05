package com.maxzuo.strategy;

/**
 * 微信支付
 *
 * Created by zfh on 2019/11/05
 */
public class WxpayStrategy implements PaymentStrategy {

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid using Wxpay.");
    }
}
