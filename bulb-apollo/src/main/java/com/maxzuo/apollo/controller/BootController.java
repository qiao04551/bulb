package com.maxzuo.apollo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zfh on 2019/12/18
 */
@RestController
public class BootController {

    @Value("${timeout:0}")
    private Integer timeout;

    @Value("${spring.jdbc.url:unknown}")
    private String jdbcUrl;

    @GetMapping("config")
    public void doConfig () {
        System.out.println("timeout: " + timeout);
        System.out.println("jdbcUrl: " + jdbcUrl);
    }
}
