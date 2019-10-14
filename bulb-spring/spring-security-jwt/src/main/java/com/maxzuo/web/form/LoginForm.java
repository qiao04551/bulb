package com.maxzuo.web.form;

import lombok.Data;

/**
 * 用户登录请求
 */
@Data
public class LoginForm {

    private String username;

    private String password;

    private Boolean rememberMe;
}
