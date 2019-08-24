//package com.maxzuo.bulb;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.mgt.DefaultSecurityManager;
//import org.apache.shiro.realm.jdbc.JdbcRealm;
//import org.apache.shiro.subject.Subject;
//import org.junit.Test;
//
///**
// * Created by zfh on 2018/09/16
// */
//public class JdbcRealmTest {
//
//    DruidDataSource dataSource = new DruidDataSource();
//
//    {
//        dataSource.setUrl("jdbc:mysql://47.98.199.80:3306/mooc");
//        dataSource.setUsername("dazuo");
//        dataSource.setPassword("dazuo.123");
//    }
//
//    @Test
//    public void testJdbcRealmTest () {
//        JdbcRealm jdbcRealm = new JdbcRealm();
//        jdbcRealm.setDataSource(dataSource);
//
//        // 自定义认证的SQL语句
//        String sql = "select password from user where username = ?";
//        jdbcRealm.setAuthenticationQuery(sql);
//
//        // 1.构建SecurityManager环境
//        DefaultSecurityManager securityManager = new DefaultSecurityManager();
//        securityManager.setRealm(jdbcRealm);
//
//        // 2.主体提交认证请求
//        SecurityUtils.setSecurityManager(securityManager);
//        UsernamePasswordToken token = new UsernamePasswordToken("dazuo1", "dazuo.123");
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(token);
//
//        System.out.println("isAuthenticated: " + subject.isAuthenticated());
//
//    }
//}
