package com.maxzuo.bulb.spring.annotation.bean;

import org.springframework.stereotype.Service;

/**
 * Created by zfh on 2019/09/19
 */
@Service("userService")
public class UserService {

    public void printName (){
        System.out.println("this is UserService");
    }
}
