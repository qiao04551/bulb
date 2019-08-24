package com.maxzuo.classloader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Vector;

/**
 * 类加载器具有等级制度，但是并非继承关系，以组合的方式来复用父加载器的功能，这也符合组合优先原则。
 * Created by zfh on 2019/01/28
 */
@DisplayName("类加载器的层级")
class ParentClassLoaderTest {

    @Test
    void main() {
        // 获取一个系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader.equals(getClass().getClassLoader()));

        // 获取系统加载器的父级加载器
        ClassLoader parentClassLoader = systemClassLoader.getParent();
        System.out.println("系统加载器的父级加载器：" + parentClassLoader);

        // 获取扩展类加载器的父级加载器；因为根加载器Bootstrap由C++编写，并不存在JVM体系内，所以输出null。
        ClassLoader parent = parentClassLoader.getParent();
        System.out.println("扩展类加载器的父级加载器：" + parent);

        try {
            Class<?> aClass = Class.forName("com.maxzuo.classloader.CustomClassLoader");
            System.out.println("检查当前类的类加载器：" + aClass.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 检查JDK 提供的Object类是由哪个类加载器负责加载的
        try {
            Class<?> aClass = Class.forName("java.lang.Object");
            System.out.println("Object类的加载器：" + aClass.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("查看当前类加载器加载的类，不包含Bootstrap ClassLoader加载的")
    @Test
    void getCurrentLoadClass() {
        try {
            /*
                1.只有类的引用被解析的时候，才会去加载所需要的类。
                2.每当加载类时，一旦引用另一种类型的代码段被解析，其类加载器就会查找该类中引用的任何类型。这个查找会委托给同一个类加载器。
                  比如：Fast类的m()方法中引用了Slow类，只有调用Fast类的m()方法时，才会加载Slow类。
             */
            Field f = ClassLoader.class.getDeclaredField("classes");
            f.setAccessible(true);
            Vector vector = (Vector) f.get(ClassLoader.getSystemClassLoader());
            System.out.println(vector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
