package com.maxzuo.shiro.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shiro 入门示例，通过配置文件读取用户、用户权限
 * <p>
 * Created by zfh on 2019/10/20
 */
public class ShiroSimple {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSimple.class);

    public static void main(String[] args) {
        User user = new User("guest", "guest");

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        // Session
        Session session = currentUser.getSession();
        session.setAttribute("name", "dazuo");

        // 登录
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (IncorrectCredentialsException e) {
                logger.info("登录密码错误！");
            } catch (UnknownAccountException e) {
                logger.info("用户不存在！");
            } catch (LockedAccountException e) {
                logger.info("账号被锁定！");
            }
        }
        logger.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        // 角色
        if (currentUser.hasRole("admin")) {
            logger.info("是管理员！");
        } else if (currentUser.hasRole("guest")) {
            logger.info("是游客！");
        } else {
            logger.info("未知角色！");
        }

        // 角色权限
        if (currentUser.isPermitted("write")) {
            logger.info("有写权限！");
        } else if (currentUser.isPermitted("read")) {
            logger.info("有读权限！");
        } else {
            logger.info("无权限！");
        }

        // 退出
        currentUser.logout();
    }
}
