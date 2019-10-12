package com.maxzuo.bulb.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zfh on 2019/09/25
 */
@RestController
public class Rest {

    @GetMapping("/test")
    public void test (String name) {
        System.out.println(name);
    }
}
