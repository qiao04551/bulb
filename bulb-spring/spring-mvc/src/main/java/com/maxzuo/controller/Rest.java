package com.maxzuo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础请求控制器
 * <p>
 * Created by zfh on 2020/05/21
 */
@RestController
public class Rest {

    @GetMapping("token")
    public String getToken() {
        return "12345";
    }
}
