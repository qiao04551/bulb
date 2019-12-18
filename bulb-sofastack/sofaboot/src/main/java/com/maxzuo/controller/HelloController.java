package com.maxzuo.controller;

import com.maxzuo.dubbo.IScUserService;
import com.maxzuo.sofa.IHelloSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zfh on 2019/12/15
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private IHelloSyncService helloSyncService;

    @Autowired
    private IScUserService scUserService;

    @GetMapping("log")
    public String log () {
        logger.warn("打印日志");
        return "hello log";
    }

    @GetMapping("hello")
    public String doHello (@RequestParam("msg") String msg) {
        String result= helloSyncService.saySync(msg);
        System.out.println(result);
        return "ok";
    }

    /**
     * 调用dubbo服务，默认的版本号：1.0
     */
    @GetMapping("user")
    public String doUser () {
        scUserService.save(1);
        return "ok";
    }
}
