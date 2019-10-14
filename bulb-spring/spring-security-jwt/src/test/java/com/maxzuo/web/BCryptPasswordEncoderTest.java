package com.maxzuo.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring BCryptPasswordEncoder 加密、加密、验证策略，一种基于随机生成salt的根据强大的哈希加密算法。
 * <p>
 * Created by zfh on 2019/10/14
 */
public class BCryptPasswordEncoderTest {

    public static void main(String[] args) {
        // encode("123456");
        match("123456", "$2a$10$igccM3trJDi7Vxiih7XtnevBIB2Txz2HpPMf4x3/GfnlSAhxBHLYC");
    }

    /**
     * 加密（运行会得到不同的加盐密码，但是这些加盐密码都会和 123456 相等）
     */
    private static void encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePW = encoder.encode(password);
        System.out.println(encodePW);
    }

    /**
     * 解密，对原密码和已加密密码进行匹配
     */
    private static void match (String password, String encodePW) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean result = encoder.matches(password, encodePW);
        System.out.println(result);
    }
}
