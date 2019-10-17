package com.maxzuo.jpa;

import com.maxzuo.jpa.entity.User;
import com.maxzuo.jpa.repositories.UserRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

/**
 * Created by zfh on 2019/10/17
 */
public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserRepository repository = context.getBean(UserRepository.class);
        Optional<User> userOptional = repository.findById(1);
        User user = userOptional.get();
        System.out.println(user);
    }
}
