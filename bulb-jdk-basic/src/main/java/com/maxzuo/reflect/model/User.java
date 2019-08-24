package com.maxzuo.reflect.model;

import sun.reflect.Reflection;

import java.io.Serializable;

/**
 * Created by zfh on 2019/01/26
 */
@Token("hello user")
public class User implements Serializable {

    private static final long serialVersionUID = 5800599219375047135L;

    private Integer           id;

    private String            name;

    private Integer           age;

    public static Integer     code;

    public User() {
    }

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Token("hello method")
    public String getParam(String param) {
        return param + " dazuo";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + '}';
    }

    public static void getCallerClass() {
        Class<?> callerClass = Reflection.getCallerClass(0);
        System.out.println("callerClass: " + callerClass);

        Class<?> firstLevelClass = Reflection.getCallerClass(1);
        System.out.println("firstLevelClass: " + firstLevelClass);

        Class<?> secondLevelClass = Reflection.getCallerClass(2);
        System.out.println("secondLevelClass: " + secondLevelClass);
    }
}
