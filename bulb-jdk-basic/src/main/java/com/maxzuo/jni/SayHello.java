package com.maxzuo.jni;
/**
 * Mac下java通过jni调动c语言
 * <pre>
 *
 *  1.编译流程
 *   // 下面命令在src/main/java目录下执行
 *   > javac com/maxzuo/jni/SayHello.java
 *   > javah -jni com.maxzuo.jni.SayHello
 *   > gcc -dynamiclib -I /Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/include -I /Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/include/darwin SayHelloImpl.c -o libSayHello.jnilib
 *   > mv SayHello.h SayHello.c ./com/maxzuo/jni
 *   > java com.maxzuo.jni.SayHello
 *
 *  2.注意JNI的动态库在linux上面的命名是 .so 文件，而在 Mac OS 上面的命名是 .jnilib。不能在MacOS 上使用linux的本机库，JVM字节代码和本机代码之间的主要区别。本机代码是特定于平台的。
 * </pre>
 * Created by zfh on 2020/09/06
 */
public class SayHello {

    public native void sayHello();

    static {
        System.load("/Users/dazuo/workplace/bulb/bulb-jdk-basic/src/main/java/com/maxzuo/jni/libSayHello.jnilib");
    }

    public static void main(String[] args) {
        SayHello sayHello = new SayHello();
        sayHello.sayHello();
    }
}
