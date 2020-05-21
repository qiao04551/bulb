package com.maxzuo.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * Agent入口
 * Created by zfh on 2019/01/30
 */
public class PerfMonAgent {

    /**
     * 探针入口方法
     * <pre>
     *  1.当命令启动该代理jar时，JVM会根据manifest中指定的代理类，使用与main类相同的类加载器加载代理类。在main方法之前执行
     *    premain方法。被统一的安全策略(security policy)和上下文(context)管理
     *  2.如果premain(String args, Instrumentation inst)和premain(String args)同时存在时，优先使用前者。
     *  3.Instrumentation#addTransformer()方法用于添加自己定义的ClassFileTransformer，来改变class文件。
     * </pre>
     * @param agentArgs 命令中的字符串参数
     * @param inst inst是运行时由JVM自动传入的Instrumentation实例，可以用于获取JVM信息。
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("========进入premain方法============");
        System.out.println("getClassLoader(): " + PerfMonAgent.class.getClassLoader());
        System.out.println("getContextClassLoader()：" + Thread.currentThread().getContextClassLoader());

        /// 注册ClassFileTransformer：定义了类加载前的预处理类，可以在这个类中对要加载的类的字节码做一些处理，譬如进行字节码增强。
        // inst.addTransformer(new ClassFileTransformer() {
        //     /*
        //         每次类加载之前，就会调用transform方法。若该方法返回null 或 new byte[0]，则不改变加载的class字节码，若返回一个byte[]数组且长度大于0，
        //         则jvm将会用返回的byte[]数组替换掉原先应该加载的字节码。
        //      */
        //     @Override
        //     public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
        //                             ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        //         // 全限定类名
        //         String targetName = "com/maxzuo/agent/Main";
        //         if (targetName.equals(className)) {
        //
        //             // 字节码文件输入的字节缓冲区（不得修改）
        //             FileUtils.writeToFile(classfileBuffer);
        //         }
        //         return new byte[0];
        //     }
        // });

        // 使用ByteBuddy动态生成类
        new AgentBuilder.Default()
            .type(ElementMatchers.any()).transform(new AgentBuilder.Transformer() {
                @Override
                public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                        TypeDescription typeDescription, ClassLoader classLoader,
                                                        JavaModule module) {
                    System.out.println("typeDescription: " + typeDescription);
                    System.out.println("classLoader: " + classLoader);
                    //System.out.println("classLoader: " + classLoader);
                    //System.out.println("module: " + module);

                    /// 篡改字节码文件
                    //builder = builder.defineField("name", String.class, Visibility.PUBLIC);
                    //FileUtils.writeToFile(builder.make().getBytes());

                    return builder;
                }
            }).installOn(inst);
    }
}
