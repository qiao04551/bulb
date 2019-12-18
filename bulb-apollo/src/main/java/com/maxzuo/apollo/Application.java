package com.maxzuo.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zfh on 2019/12/18
 */
@EnableApolloConfig
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Apollo环境配置
        System.setProperty("env", "DEV");

        SpringApplication.run(Application.class, args);
    }
}
