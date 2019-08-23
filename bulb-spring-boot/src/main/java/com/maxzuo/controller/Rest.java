package com.maxzuo.controller;

import com.maxzuo.form.UserInfoForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器入口
 * Created by zfh on 2019/08/23
 */
@RestController
public class Rest {

    @PostMapping("hello")
    public String hello (@RequestParam("name") String name) {
        System.out.println(name);
        return "hello world!";
    }

    @PostMapping("json")
    public String json (@RequestBody UserInfoForm form) {
        System.out.println(form);
        return "hello world!";
    }
}
