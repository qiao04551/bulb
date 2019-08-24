package com.maxzuo.printtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zfh on 2018/12/10
 */
@SpringBootApplication
public class PrintTemplateApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(PrintTemplateApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许的路径
        registry.addMapping("/**")
            // 允许的域名
            .allowedOrigins("*")
            // 允许证书
            .allowCredentials(true)
            // 允许的请求类型
            .allowedMethods("GET", "POST")
            // 允许的头部
            .allowedHeaders("token", "Origin", "X-Requested-With", "Content-Type", "Accept");
    }
}
