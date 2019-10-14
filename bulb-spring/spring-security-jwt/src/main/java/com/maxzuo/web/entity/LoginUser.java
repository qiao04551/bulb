package com.maxzuo.web.entity;

import lombok.Data;

/**
 * 登录请求
 */
@Data
public class LoginUser {

    private String username;
    private String password;
    private Boolean rememberMe;
}
