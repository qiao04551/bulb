package com.maxzuo.io;

import java.io.*;

/**
 * 序列化与ObjectInputStream、ObjectOutputStream
 * <p>
 * Created by zfh on 2019/06/16
 */
public class ObjectInputStreamExample {

    public static void main(String[] args) {
        try {
            // 序列化对象写入字节数组
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(new User(23, "dazuo"));
            oos.close();

            // 从字节数组中反序列化对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            User user = (User) ois.readObject();
            System.out.println("User.toString()：" + user);
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实体，实现Serializable接口
     */
    private static class User implements Serializable {

        private static final long serialVersionUID = -3218325068382437706L;

        private Integer age;

        private String name;

        public User(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

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

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
