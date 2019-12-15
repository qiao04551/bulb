package com.maxzuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by zfh on 2019/12/15
 */
@ImportResource({"classpath:generic-consumer.xml"})
@SpringBootApplication
public class GenericApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericApplication.class, args);
    }
}
