package com.maxzuo.builder;

/**
 * builder 模式
 * <p>
 * Created by zfh on 2019/09/23
 */
public class BuilderExample {

    public static void main(String[] args) {
        // 调用方式
        Student student = Student.builder().age(22).name("dazuo").build();
        System.out.println(student);
    }
}
