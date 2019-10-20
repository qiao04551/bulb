package com.maxzuo.shiro.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户类
 * <p>
 * Created by zfh on 2019/10/20
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private String password;
}
