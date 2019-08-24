package com.maxzuo.proxy.bytebuddy;


import com.maxzuo.proxy.bytebuddy.model.Boss;
import com.maxzuo.proxy.bytebuddy.model.Employee;
import com.maxzuo.proxy.bytebuddy.model.Fast;
import com.maxzuo.proxy.bytebuddy.model.Slow;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用ByteBuddy创建类和加载类
 * Created by zfh on 2019/01/28
 */
@DisplayName("创建类和加载类")
class CreateAndLoadClassTest {

    /**
     * <pre>
     *  1.由 Byte Buddy 创建的类型使用 DynamicType.Unloaded 的实例表示。顾名思义，这些类型不会加载到 Java 虚拟机中。
     *    相反，由 Byte Buddy 创建的类以二进制，Java 类文件格式形式表示
     *  2.DynamicType.Unloaded 类允许提取表示动态类型的字节数组。
     * </pre>
     */
    @DisplayName("创建类")
    @Test
    void testCreateClass() {
        // 运行时创建动态类型（类）；使用 DynamicType.Unloaded 的实例表示。
        DynamicType.Unloaded<Employee> dynamicType = new ByteBuddy()
        // 创建子类；如果提供的类型是接口，则创建实现此接口的类，不会实现接口中的方法！
            .subclass(Employee.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
            // 默认命名策略下，它基于动态类型的超类名称来随机生成类名。
            // 可以提供的名称命名动态类型。名称需要是完全限定的。
            .name("com.maxzuo.demo.ByteBuddyDemo").make();

        /// 1.使用saveIn(File)方法将类存储在给定的文件夹中
        //dynamicType.saveIn(new File("bytecode"));

        // 2.使用 inject(File) 方法将类注入到现有的 Jar 文件中。

        // 3.将类的字节数组存储到指定文件中
        writeToFile(dynamicType.getBytes());
    }

    /**
     * 将字节数组写入.class文件中
     * @param bytes 字节数组
     */
    private void writeToFile(byte[] bytes) {
        File file = new File("ByteBuddy.class");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.flush();
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @DisplayName("加载类")
    @Test
    void testLoadClass() throws IllegalAccessException, InstantiationException {
        Class<? extends Boss> aClass = new ByteBuddy().subclass(Boss.class).name("com.maxzuo.demo.ByteBuddyDemo")
            .make()
            // ClassLoadingStrategy 加载此类
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();

        Boss boss = aClass.newInstance();
        // 输出：i am boss
        boss.talk();
    }

    /**
     * 以上都是 创建子类 的实例，所有类可以类似地通过重定义或重定基类来定义。
     * <pre>
     *  1.当重定义一个类时，Byte Buddy可以对一个已有的类添加属性和方法，或者删除已经存在的方法实现。
     *   如果使用其他的方法实现替换已经存在的方法实现，则原来存在的方法实现就会消失。
     *
     *  2.当重定基底一个类时，Byte Buddy 保存基底类所有方法的实现。当 Byte Buddy 如执行类型重定义时，它将所有这些方法实现复制
     *   到具有兼容签名的重新命名的私有方法中，而不是抛弃重写的方法
     * </pre>
     */
    @DisplayName("重新定义或者重定基底已经存在的类")
    @Test
    void testRedefineAndRebase() {
        // 重定义类
        DynamicType.Unloaded<Object> dynamicType1 = new ByteBuddy().redefine(Object.class).make();

        // 重定基底
        DynamicType.Unloaded<Object> dynamicType2 = new ByteBuddy().rebase(Object.class).make();
    }

    @DisplayName("操作没有加载的类")
    @Test
    void testNotLoadClass() throws NoSuchFieldException {
        TypePool typePool = TypePool.Default.ofClassPath();
        new ByteBuddy()
            // 重定义类
            .redefine(typePool.describe("com.maxzuo.bytebuddy.model.Boss").resolve(),
                ClassFileLocator.ForClassLoader.ofClassPath()).defineField("age", Integer.class).make()
            .load(ClassLoader.getSystemClassLoader());

        System.out.println("字段名：" + Boss.class.getDeclaredField("age"));
    }

    @DisplayName("重新加载类")
    @Test
    void testReloadClass() {
        // 安装代理，从代理中加载类型
        ByteBuddyAgent.install();
        Fast fast = new Fast();
        System.out.println("old result：" + fast.m());
        new ByteBuddy()
            // 重定基底
            .redefine(Slow.class).name(Fast.class.getName()).make()
            .load(ClassLoader.getSystemClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        System.out.println("new result：" + fast.m());
    }
}
