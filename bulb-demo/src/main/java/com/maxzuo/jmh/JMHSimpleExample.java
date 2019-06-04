package com.maxzuo.jmh;

/**
 * Java微基准测试框架JMH-简单示例
 * <p>
 * Created by zfh on 2019/06/04
 */
public class JMHSimpleExample {

    /*
        简介：
          JMH，即Java Microbenchmark Harness，这是专门用于进行代码的微基准测试的一套工具API。

        比较典型的应用场景：
          1.当你已经找出了热点函数，而需要对热点函数进行进一步的优化时，就可以使用 JMH 对优化的效果进行定量的分析。
          2.想定量地知道某个函数需要执行多长时间，以及执行时间和输入 n 的相关性
          3.一个函数有两种不同实现（例如JSON序列化/反序列化有Jackson和Gson实现），不知道哪种实现性能更好

        添加Maven依赖：
         <dependency>
             <groupId>org.openjdk.jmh</groupId>
             <artifactId>jmh-core</artifactId>
             <version>${jmh.version}</version>
         </dependency>
         <dependency>
             <groupId>org.openjdk.jmh</groupId>
             <artifactId>jmh-generator-annprocess</artifactId>
             <version>${jmh.version}</version>
         </dependency>

         基本概念
           Mode表示JMH进行Benchmark时所使用的模式。通常是测量的维度不同，或是测量的方式不同。目前 JMH 共有四种模式：
            - Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用”。
            - AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”。
            - SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
            - SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。
              往往同时把 warmup 次数设为0，用于测试冷启动时的性能。
         */

    public static void main(String[] args) {
    }
}
