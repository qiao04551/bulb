package com.maxzuo.bulb.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import com.maxzuo.bulb.hessian.service.IBasic;

/**
 * Hessian 客户端
 * <p>
 * Created by zfh on 2019/08/13
 */
public class HessianClient {

    public static void main(String[] args) {
        try {
            String url = "http://localhost:8080/hessian";

            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            IBasic basic = (IBasic) factory.create(IBasic.class, url);

            System.out.println(basic.sayHello("dazuo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
