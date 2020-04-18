package com.maxzuo.elastic.model;

/**
 * Created by zfh on 2019/01/25
 */
public class Person {

    private String user;

    private String title;

    private String desc;

    public Person() {
    }

    public Person(String user, String title, String desc) {
        this.user = user;
        this.title = title;
        this.desc = desc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Person{" + "user='" + user + '\'' + ", title='" + title + '\'' + ", desc='" + desc + '\'' + '}';
    }
}
