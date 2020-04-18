package com.maxzuo.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 数据表映射实体对象
 * <p>
 * Created by zfh on 2019/04/21
 */
@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue
    private Integer id;

    private String  username;

    private String  password;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
