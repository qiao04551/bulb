package com.maxzuo.spi;

/**
 * 微信支付
 * <p>
 * Created by zfh on 2019/11/14
 */
@Spi(order = 1)
public class WxOnlinePay implements IOnlinePay{

    @Override
    public void pay(int amount) {
        System.out.println("微信支付：" + amount + "元");
    }
}
