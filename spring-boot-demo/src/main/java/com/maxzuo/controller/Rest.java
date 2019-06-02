package com.maxzuo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zfh on 2019/06/02
 */
@RestController
public class Rest {

    private static final Logger logger = LoggerFactory.getLogger(Rest.class);

    @GetMapping("hello")
    public String hello () {
        logger.info("hello world！");
        return "hello world！";
    }
}
