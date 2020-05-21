package com.maxzuo.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * Hessian客户端
 * <p>
 * Created by zfh on 2020/05/21
 */
public class HessianClient {

    public static void main(String[] args) {
        try {
            String url = "http://localhost:8080/token";

            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            TokenService tokenService = (TokenService) factory.create(TokenService.class, url);

            System.out.println(tokenService.getToken(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
