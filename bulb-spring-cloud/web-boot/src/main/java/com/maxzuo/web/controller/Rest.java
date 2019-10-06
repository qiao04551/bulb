package com.maxzuo.web.controller;

import com.maxzuo.web.form.UserInfoForm;
import com.maxzuo.web.service.IScSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器入口
 * Created by zfh on 2019/08/23
 */
@RestController
public class Rest {

    @Value("${env}")
    private String env;

    @Autowired
    private IScSysUserService scSysUserService;

    @PostMapping("hello")
    public String hello (@RequestParam("name") String name) {
        String result = scSysUserService.hello(name);
        System.out.println("result: " + result);
        return "hello world!";
    }

    @PostMapping("json")
    public String json (@RequestBody UserInfoForm form) {
        System.out.println(form);
        return "hello world!";
    }

    @GetMapping("env")
    public String doEnv () {
        System.out.println("env：" + env);
        return "hello getEnv";
    }
}
