package com.maxzuo.dubbo;

/**
 * 服务实现类
 * <p>
 * Created by zfh on 2020/05/21
 */
public class TokenServiceImpl implements TokenService {

    @Override
    public String getToken(Integer id) {
        return "Token:" + id;
    }
}
