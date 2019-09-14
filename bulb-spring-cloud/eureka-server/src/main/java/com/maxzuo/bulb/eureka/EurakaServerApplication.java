package com.maxzuo.bulb.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by zfh on 2019/09/14
 */
@SpringBootApplication
@EnableEurekaServer
public class EurakaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakaServerApplication.class, args);
    }
}
