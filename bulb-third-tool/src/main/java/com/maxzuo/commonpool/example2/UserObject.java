package com.maxzuo.commonpool.example2;

/**
 * 用户对象
 * <p>
 * Created by zfh on 2019/09/20
 */
public class UserObject {

    private Integer age;

    private String name;

    private Long timestamp;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
