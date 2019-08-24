//package com.maxzuo.bulb;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.mgt.DefaultSecurityManager;
//import org.apache.shiro.realm.SimpleAccountRealm;
//import org.apache.shiro.subject.Subject;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Shiro Authentication 认证
// * Created by zfh on 2018/09/16
// */
//public class AuthenticationTest {
//
//    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
//
//    @Before
//    public void addUser () {
//        simpleAccountRealm.addAccount("mark", "123456" , "admin");
//    }
//
//    @Test
//    public void testAuthentication () {
//        // 1.构建SecurityManager环境
//        DefaultSecurityManager securityManager = new DefaultSecurityManager();
//        securityManager.setRealm(simpleAccountRealm);
//
//        // 2.主体提交认证
//        SecurityUtils.setSecurityManager(securityManager);
//        Subject subject = SecurityUtils.getSubject();
//
//        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123456");
//        // 3.登录
//        try {
//            subject.login(token);
//        } catch (Exception e) {
//            System.out.println("exception message: " + e.getMessage());
//        }
//        System.out.println("isAuthenticated:" + subject.isAuthenticated());
//
//        // 4.检查授权
//        Boolean aBoolean = subject.hasRole("admin");
//        if (aBoolean) {
//            System.out.println("checkRole: " + true);
//        }
//
//        // 5.退出
//        subject.logout();
//        System.out.println("isAuthenticated:" + subject.isAuthenticated());
//    }
//}
