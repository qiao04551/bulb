package com.maxzuo.proxy.cglib;

import net.sf.cglib.beans.BeanCopier;

/**
 * 使用CGlib实现Bean拷贝(BeanCopier)
 * <pre>
 *   1.属性名称相同类型相同的属性拷贝OK。
 *   2.属性名称相同而类型不同的属性不会被拷贝；即使源类型是原始类型(int, short和char等)，目标类型是
 *     其包装类型(Integer, Short和Character等)，或反之：都 不会被拷贝。
 *   3.当源和目标类的属性类型不同时，不能拷贝该属性，此时我们可以通过实现Converter接口来自定义转换器
 * </pre>
 * Created by zfh on 2019/07/05
 */
public class BeanCopierExample {

    public static void main(String[] args) {
        User user = new User();
        user.setName("dazuo");
        user.setAge(23);

        UserVO userVO = new UserVO();

        BeanCopier beanCopier = BeanCopier.create(User.class, UserVO.class, false);
        beanCopier.copy(user, userVO, null);
        System.out.println(userVO);
    }

    private static class User {

        private Integer age;

        private String name;

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

    private static class UserVO {

        Integer id;

        private String name;

        private String age;

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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserVO{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }
}
