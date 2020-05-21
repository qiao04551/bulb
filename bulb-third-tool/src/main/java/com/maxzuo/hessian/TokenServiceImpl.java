package com.maxzuo.hessian;

import com.caucho.hessian.server.HessianServlet;

/**
 * 服务实现类
 * <p>
 * Created by zfh on 2020/05/21
 */
public class TokenServiceImpl extends HessianServlet implements TokenService {

    private static final long serialVersionUID = 1783894834959515333L;

    @Override
    public String getToken(Integer id) {
        return "Token:" + id;
    }
}
