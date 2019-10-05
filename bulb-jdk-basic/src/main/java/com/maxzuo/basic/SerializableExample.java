package com.maxzuo.basic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化和反序列化
 * <pre>
 *   1.什么是对象的序列化？
 *     序列化：把对象转化成字节序列的过程就是对象的序列化；
 *     反序列化：把字节序列转化成对象的过程就是对象的反序列化。
 *   2.对象序列化的用途
 *     Java程序在运行中，对象都是分配在内存中，而序列化的一个用途就是将内存的中对象转化成磁盘中的对象。对象不能直接在网络中传输，
 *     有时候需要将对象转成字节序列在网络中传输，这就是对象序列化的第二个用途。
 *   3.如何实现对象的序列化？
 *     实现java.io.Serializable接口
 *     java.io.ObjectOutputStream代表对象输出流，它的writeObject(Object obj)方法可对参数指定的obj对象进行序列化，把得到的字节序列写到一个目标输出流中。
 *     java.io.ObjectInputStream代表对象输入流，它的readObject()方法从一个源输入流中读取字节序列，再把它们反序列化为一个对象，并将其返回。
 *   4.serialVersionUID的取值
 *     serialVersionUID的取值是Java运行时环境根据类的内部细节自动生成的。如果对类的源代码作了修改，再重新编译，新生成的类文件
 *     的serialVersionUID的取值有可能也会发生变化。类的serialVersionUID的默认值完全依赖于Java编译器的实现，对于同一个类，用
 *     不同的Java编译器编译，有可能会导致不同的 serialVersionUID，也有可能相同。
 *   5.transient关键字
 *     Java中使用transient关键字修饰的成员属性变量，在类的序列化时将被忽略。
 * </pre>
 * Created by zfh on 2019/10/05
 */
public class SerializableExample {

    public static void main(String[] args) {
        arrayListSerialize();
    }

    /**
     * ArrayList 对象的序列化
     * <pre>
     *   如果一个类不仅实现了Serializable接口，而且定义了readObject（ObjectInputStream in）和 writeObject(ObjectOutputStream out)方法，
     *   那么将按照如下的方式进行序列化和反序列化：
     *   1）ObjectOutputStream会调用这个类的writeObject方法进行序列化，ObjectInputStream会调用相应的readObject方法进行反序列化。
     *   2）通过反射机制实现，ObjectOutputStream可以知道一个类是否实现了writeObject/readObject方法。
     *     {@link ObjectStreamClass#invokeWriteObject(java.lang.Object, java.io.ObjectOutputStream)}
     * </pre>
     */
    private static void arrayListSerialize () {
        List<Integer> numList = new ArrayList<>(10);
        numList.add(12123);
        numList.add(3231);
        numList.add(8090);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 序列化
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(numList);

            // 反序列化
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            List list = (List) ois.readObject();
            System.out.println(list);
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通对象的序列化/反序列化
     */
    private static void commonObjectSerialize () {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 序列化
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(new User(22, "dazuo", 1));
            oos.close();

            // 反序列化
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            User user = (User) ois.readObject();
            System.out.println(user.toString());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class User implements Serializable {

    private static final long serialVersionUID = 4049497031281291153L;

    private Integer age;

    private String name;

    private transient Integer gender;

    User(Integer age, String name, Integer gender) {
        this.age = age;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
