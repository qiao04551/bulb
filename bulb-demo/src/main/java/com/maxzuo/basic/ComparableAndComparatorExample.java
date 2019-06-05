package com.maxzuo.basic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Java中Comparable和Comparator区别
 * <pre>
 *   1.Comparable是排序接口。若一个类实现了Comparable接口，就意味着该类支持排序。实现了Comparable接口的类的对象的列表
 *     或数组可以通过Collections.sort或Arrays.sort进行自动排序。
 *     此外，实现此接口的对象可以用作有序映射中的键或有序集合中的集合，无需指定比较器。
 *     此接口只有一个方法compare，比较此对象与指定对象的顺序，如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。
 *
 *   2.Comparator是比较接口，我们如果需要控制某个类的次序，而该类本身不支持排序(即没有实现Comparable接口)，那么我们就可以建立
 *     一个“该类的比较器”来进行排序，这个“比较器”只需要实现Comparator接口即可。也就是说，我们可以通过实现Comparator来新建一个比较器，
 *     然后通过这个比较器对类进行排序。
 *
 *   3.Comparable是排序接口，若一个类实现了Comparable接口，就意味着“该类支持排序”。而Comparator是比较器，我们若需要控制某个类的次序，
 *     可以建立一个“该类的比较器”来进行排序。
 *     Comparable相当于“内部比较器”，而Comparator相当于“外部比较器”。
 * </pre>
 * Created by zfh on 2019/06/05
 */
class ComparableAndComparatorExample {

    /**
     * 实现Comparable接口
     */
    @Test
    void testComparableSort () {
        Employee[] employees = new Employee[3];
        employees[0] = new Employee("da", 22);
        employees[1] = new Employee("zuo", 10);
        employees[2] = new Employee("wang", 18);
        Arrays.sort(employees);
        System.out.println(Arrays.toString(employees));
    }

    /**
     * 实现Comparable接口的compareTo方法
     */
    private static class Employee implements Comparable<Employee>{

        private String username;

        private Integer age;

        public Employee (String username, Integer age) {
            this.username = username;
            this.age = age;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "username='" + username + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public int compareTo(Employee var1) {
            if (this.age > var1.getAge()) {
                return 1;
            } else if (this.age < var1.getAge()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * 使用外部排序器
     */
    @Test
    void testComparatorSort () {
        Book[] books = new Book[3];
        books[0] = new Book("风雨火", 39);
        books[1] = new Book("鸟语歌", 28);
        books[2] = new Book("清流", 48);

        Arrays.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getPrice() - o2.getPrice();
            }
        });
        System.out.println(Arrays.toString(books));
    }

    private static class Book {

        private String name;

        private Integer price;

        public Book(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
