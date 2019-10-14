package com.maxzuo.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
@Data
@AllArgsConstructor
public class User {

    private Integer id;

    private String username;

    private String password;

    public List<SimpleGrantedAuthority> getRoles() {
        String roles = "admin";
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return authorities;
    }
}
