package com.maxzuo.producer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供者
 * <p>
 * Created by zfh on 2019/09/14
 */
@RestController
public class ProducerRest {

    @PostMapping("hello")
    public String producer (@RequestParam("name") String name) {
        System.out.println("this is producer!!!");
        return "hello " + name;
    }
}
