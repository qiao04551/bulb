package com.maxzuo.strategy;

/**
 * 店铺购物车结算，两种支付方式
 * <p>
 * Created by zfh on 2019/11/05
 */
public class ShopCart {

    public static void main(String[] args) {
        ShopCart shopCart = new ShopCart();
        int payType = 1;

        switch (payType) {
            case 1:
                shopCart.pay(new AlipayStrategy());
                break;
            case 2:
                shopCart.pay(new WxpayStrategy());
                break;
            default:
        }
    }

    private void pay (PaymentStrategy paymentStrategy) {
        paymentStrategy.pay(calculateTotal());
    }

    private int calculateTotal() {
        return 10;
    }
}
