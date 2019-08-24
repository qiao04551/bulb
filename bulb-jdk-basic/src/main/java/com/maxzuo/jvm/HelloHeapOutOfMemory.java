package com.maxzuo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出测试
 * <pre>
 *  1.JVM性能调优参数
 *    -Xms256M -Xmx1024M，其中-X表示它是JVM运行参数，ms是memory start的简称，mx是memory max的简称，分别代表最小堆容量和最大堆容量。
 *    但是，在通常情况下，服务器在运行过程中，堆空间不断地扩容与回缩，势必形成不必要的系统压力，所以在线上环境中，JVM的Xms和Xmx设置成
 *    一样大小，避免GC后调整堆大小时带来的额外压力。
 *    -XX:MaxTenuringThreashold 配置计数器的值达到某个阈值的时候，对象从新生代晋升至老年代。默认值是 15。
 *    -XX:+HeapDumpOnOutOfMemoryError 当JVM遇到OOM异常时能够输出堆内信息。
 *    -XX:+PrintGCDetails 输出GC的触发情况
 *  2.JVM进行垃圾回收
 *    System.gc()方法是建议垃圾收集器尽快进行垃圾收集，具体何时执行仍由JVM来判断。
 *    System.runFinalization()方法的作用是强制调用已经失去引用对象的finalize()
 *  3.测试VM的参数
 *    -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -Xms10m -Xmx10m -XX:HeapDumpPath=F:/bulb-demo/heapdump.hprof
 * </pre>
 * Created by zfh on 2019/02/14
 */
public class HelloHeapOutOfMemory {

    public static void main(String[] args) {
        System.out.println("HelloHeapOutOfMemory");
        List<Person> persons = new ArrayList<>(10);
        int counter = 0;
        while (true) {
            persons.add(new Person());
            System.out.println("Instance：" + (++counter));
        }
    }
}

class Person {
}
