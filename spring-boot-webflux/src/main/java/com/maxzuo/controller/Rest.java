package com.maxzuo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zfh on 2019/06/04
 */
@RestController
public class Rest {

    /**
     * 基于WebMVC注解的方式
     */
    @GetMapping("hello")
    public String hello () {
        return "hello wrold！";
    }
}
