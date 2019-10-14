package com.maxzuo.web.service;

import com.maxzuo.web.entity.JwtUser;
import com.maxzuo.web.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // Mock 数据库，密码使用 BCryptPasswordEncoder 加密
        return new JwtUser(new User(1, name, "$2a$10$igccM3trJDi7Vxiih7XtnevBIB2Txz2HpPMf4x3/GfnlSAhxBHLYC"));
    }
}
