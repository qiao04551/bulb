package com.maxzuo.builder;

/**
 * Created by zfh on 2019/09/23
 */
public class Student {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static Builder builder () {
        return new Builder();
    }

    public static class Builder {

        private String name;

        private int age;

        public Builder name (String name) {
            this.name = name;
            return this;
        }

        public Builder age (int age) {
            this.age = age;
            return this;
        }

        public Student build () {
            Student student = new Student();
            student.setAge(age);
            student.setName(name);
            return student;
        }
    }
}
