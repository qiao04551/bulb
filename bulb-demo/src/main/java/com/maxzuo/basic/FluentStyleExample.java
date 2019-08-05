package com.maxzuo.basic;

import org.apache.curator.framework.CuratorFrameworkFactory;

import java.util.Date;

/**
 * fluent 风格编程
 * <pre>
 *   参考Curator-framework 的工厂模式fluent风格操作
 *   {@link CuratorFrameworkFactory#newClient(java.lang.String, org.apache.curator.RetryPolicy)}
 * </pre>
 * <p>
 * Created by zfh on 2019/08/05
 */
public class FluentStyleExample {

    public static void main(String[] args) {
        User user = new User();
        user.setId(1)
            .setName("dazuo")
            .setAge(23)
            .setGender(1)
            .setCreateTime(new Date());
    }
}

class User {

    private Integer id;

    private String name;

    private Integer age;

    private Integer gender;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public User setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", createTime=" + createTime +
                '}';
    }
}
