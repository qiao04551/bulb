package com.maxzuo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zfh on 2019/05/30
 */
@RestController
public class Rest {

    private final static Logger logger = LoggerFactory.getLogger(Rest.class);

    @GetMapping("test")
    public String test() {
        return "hello world";
    }
}
