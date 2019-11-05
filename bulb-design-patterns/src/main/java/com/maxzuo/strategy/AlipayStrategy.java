package com.maxzuo.strategy;

/**
 * 支付宝支付
 *
 * Created by zfh on 2019/11/05
 */
public class AlipayStrategy implements PaymentStrategy {

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid using Alipay.");
    }
}
