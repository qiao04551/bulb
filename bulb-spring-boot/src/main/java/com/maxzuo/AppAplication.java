package com.maxzuo;

import com.maxzuo.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 启动类
 * <p>
 * Created by zfh on 2019/06/02
 */
@SpringBootApplication
public class AppAplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AppAplication.class, args);
        UserService bean = context.getBean(UserService.class);
        System.out.println(bean);
    }

    /**
     * 注解@ConditionalOnBean表示容器中存在该类型Bean时起效
     */
    // @ConditionalOnBean(TokenService.class)
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
