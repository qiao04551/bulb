package com.maxzuo.basic;

import org.junit.Test;

/**
 * 实现深拷贝的两种方式（逐层实现cloneable接口，序列化的方式来实现）
 * <pre>
 *  1.在浅克隆中，如果原型对象的成员变量是值类型，将复制一份给克隆对象；如果原型对象的成员变量是引用类型，
 *    则将引用对象的地址复制一份给克隆对象，也就是说原型对象和克隆对象的成员变量指向相同的内存地址
 *    简单来说，在浅克隆中，当对象被复制时只复制它本身和其中包含的值类型的成员变量，而引用类型的成员对象并没有复制。
 *  2.在深克隆中，无论原型对象的成员变量是值类型还是引用类型，都将复制一份给克隆对象，深克隆将原型对象的所有引用对象也复制一份给克隆对象。
 *    简单来说，在深克隆中，除了对象本身被复制外，对象所包含的所有成员变量也将复制。
 * </pre>
 * <p>
 * Created by zfh on 2019/05/26
 */
public class DeepCopyExample {

    @Test
    public void testCloneableInterface () {
        // testCopyFirst();
        testCopyNestd();
    }

    /**
     * 拷贝表层对象
     */
    private void testCopyFirst () {
        User user = new User("dazuo", 22);

        User cloneUser = user.clone();
        System.out.println("Modify before user: " + user);
        System.out.println("Modify before cloneUser: " + cloneUser);

        user.setName("wang");
        user.setAge(20);

        System.out.println("Modify before user: " + user);
        System.out.println("Modify after cloneUser: " + cloneUser);
    }

    /**
     * 拷贝嵌套对象
     */
    private void testCopyNestd () {
        User user = new User("dazuo", 23);
        user.setAddress(new Address("wuhan"));

        User cloneUser = user.clone();
        System.out.println("before: " + cloneUser);

        user.getAddress().setCity("beijin");

        System.out.println("after: " + cloneUser);
    }

    /**
     * 用户实体对象
     */
    static class User implements Cloneable {

        private String name;

        private Integer age;

        private Address address;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
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

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address=" + address +
                    '}';
        }

        @Override
        protected User clone() {
            User user = null;
            try {
                // 拷贝表层
                user = (User) super.clone();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (user != null) {
                // 拷贝内层
                user.address = address.clone();
            }
            return user;
        }
    }

    /**
     * 模拟嵌套内层对象
     */
    static class Address implements Cloneable {

        private String city;

        public Address(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "city='" + city + '\'' +
                    '}';
        }

        @Override
        protected Address clone() {
            Address address = null;
            try {
                // 拷贝
                address = (Address) super.clone();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return address;
        }
    }
}
