package com.maxzuo.jpa.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by zfh on 2019/10/17
 */
@Data
@ToString
@Entity
@Table(name = "sc_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
