package com.maxzuo.shiro.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义Realm，且md5 加盐加密
 * <p>
 * Created by zfh on 2019/10/20
 */
public class CustomRealm {

    private static final Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    public static void main(String[] args) {
        User user = new User("root", "123456");

        // 定义哈希散列类
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);

        // 默认的安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager(new UserRealm(credentialsMatcher));
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            currentUser.login(token);
            logger.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        } catch (IncorrectCredentialsException e) {
            logger.info("登录密码错误！");
        }

        System.out.println("is admin: " + currentUser.hasRole("admin"));
        System.out.println("permit wirte：" + currentUser.isPermitted("write"));
    }
}
