## 代理模式

代理模式是Java常见的设计模式之一。所谓代理模式是指客户端并不直接调用实际的对象，而是通过调用代理，来间接的调用实际的对象。

### 代理模式的实现

代理模式可以有两种实现的方式，一种是静态代理类，另一种是各大框架都喜欢的动态代理。

**静态代理**

静态代理的实现比较简单，代理类通过实现与目标对象相同的接口，并在类中维护一个代理对象。通过构造器塞入目标对象，赋值给代理对象，
进而执行代理对象实现的接口方法，并实现前拦截，后拦截等所需的业务功能。

- 优点：可以做到不对目标对象进行修改的前提下，对目标对象进行功能的扩展和拦截。

- 缺点：因为代理对象，需要实现与目标对象一样的接口，会导致代理类十分繁多，不易维护，同时一旦接口增加方法，则目标对象和代理类都需要维护。

**动态代理**

动态代理是指动态的在内存中构建代理对象（需要我们制定要代理的目标对象实现的接口类型）

- 优点：代理对象无需实现接口，免去了编写很多代理类的烦恼，同时接口增加方法也无需再维护目标对象和代理对象，只需在事件处理器中添加对方法的判断即可。

- 缺点：代理对象不需要实现接口，但是目标对象一定要实现接口，否则无法使用JDK动态代理。


### JDK代理

JDK动态代理是代理模式的一种实现方式，其只能代理接口。

> 使用步骤

- 1.新建一个接口

- 2.为接口创建一个实现类（要代理的真实对象）

- 3.创建代理类实现java.lang.reflect.InvocationHandler接口

> JDK动态代理的实现原理

- 1.为接口创建代理类的字节码文件

- 2.使用ClassLoader将字节码文件加载到JVM

- 3.创建代理类实例对象，执行对象的目标方法

其中：

1.保存生成的代理类的字节码文件，可以设置sun.misc.ProxyGenerator.saveGeneratedFiles 的值为true,所以代理类的字节码内容保存在项目
根目录下，文件名为$Proxy0.class；System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

2.动态代理涉及到的主要类：

```
java.lang.reflect.Proxy
java.lang.reflect.InvocationHandler
java.lang.reflect.WeakCache
sun.misc.ProxyGenerator
```

首先看Proxy类中的newProxyInstance方法调用getProxyClass0方法生成代理类的字节码文件。

其中缓存使用的是WeakCache实现的，此处主要关注使用ProxyClassFactory创建代理的情况。ProxyClassFactory是Proxy类的静态内部类，
实现了BiFunction接口，实现了BiFunction接口中的apply方法。

当WeakCache中没有缓存相应接口的代理类，则会调用ProxyClassFactory类的apply方法来创建代理类。

```java
private static final class ProxyClassFactory
            implements BiFunction<ClassLoader, Class<?>[], Class<?>>
    {
        // 代理类前缀
        private static final String proxyClassNamePrefix = "$Proxy";
        // 生成代理类名称的计数器
        private static final AtomicLong nextUniqueNumber = new AtomicLong();
        @Override
        public Class<?> apply(ClassLoader loader, Class<?>[] interfaces) {

            Map<Class<?>, Boolean> interfaceSet = new IdentityHashMap<>(interfaces.length);
            for (Class<?> intf : interfaces) {
                /*
                 * 校验类加载器是否能通过接口名称加载该类
                 */
                Class<?> interfaceClass = null;
                try {
                    interfaceClass = Class.forName(intf.getName(), false, loader);
                } catch (ClassNotFoundException e) {
                }
                if (interfaceClass != intf) {
                    throw new IllegalArgumentException(
                            intf + " is not visible from class loader");
                }
                /*
                 * 校验该类是否是接口类型
                 */
                if (!interfaceClass.isInterface()) {
                    throw new IllegalArgumentException(
                            interfaceClass.getName() + " is not an interface");
                }
                /*
                 * 校验接口是否重复
                 */
                if (interfaceSet.put(interfaceClass, Boolean.TRUE) != null) {
                    throw new IllegalArgumentException(
                            "repeated interface: " + interfaceClass.getName());
                }
            }

            String proxyPkg = null;     // 代理类包名
            int accessFlags = Modifier.PUBLIC | Modifier.FINAL;

            /*
             * 非public接口，代理类的包名与接口的包名相同
             */
            for (Class<?> intf : interfaces) {
                int flags = intf.getModifiers();
                if (!Modifier.isPublic(flags)) {
                    accessFlags = Modifier.FINAL;
                    String name = intf.getName();
                    int n = name.lastIndexOf('.');
                    String pkg = ((n == -1) ? "" : name.substring(0, n + 1));
                    if (proxyPkg == null) {
                        proxyPkg = pkg;
                    } else if (!pkg.equals(proxyPkg)) {
                        throw new IllegalArgumentException(
                                "non-public interfaces from different packages");
                    }
                }
            }

            if (proxyPkg == null) {
                // public代理接口，使用com.sun.proxy包名
                proxyPkg = ReflectUtil.PROXY_PACKAGE + ".";
            }

            /*
             * 为代理类生成名字
             */
            long num = nextUniqueNumber.getAndIncrement();
            String proxyName = proxyPkg + proxyClassNamePrefix + num;

            /*
             * 真正生成代理类的字节码文件的地方
             */
            byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                    proxyName, interfaces, accessFlags);
            try {
                // 使用类加载器将代理类的字节码文件加载到JVM中
                return defineClass0(loader, proxyName,
                        proxyClassFile, 0, proxyClassFile.length);
            } catch (ClassFormatError e) {
                throw new IllegalArgumentException(e.toString());
            }
        }
    }
```

在ProxyClassFactory类的apply方法中可看出真正生成代理类字节码的地方是ProxyGenerator类中的generateProxyClass，该类未开源，
但是可以使用IDEA、或者反编译工具jd-gui来查看。

```
public static byte[] generateProxyClass(final String var0, Class<?>[] var1, int var2) {
    ProxyGenerator var3 = new ProxyGenerator(var0, var1, var2);
    final byte[] var4 = var3.generateClassFile();
    // 是否要将生成代理类的字节码文件保存到磁盘中
    if (saveGeneratedFiles) {
        // ....
    }
    return var4;
}
```

反编译$Proxy0.class文件如下：

```
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sun.proxy;

import com.lnjecit.proxy.Subject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy0 extends Proxy implements Subject {
    private static Method m1;
    private static Method m3;
    private static Method m2;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return ((Boolean)super.h.invoke(this, m1, new Object[]{var1})).booleanValue();
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void doSomething() throws  {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return ((Integer)super.h.invoke(this, m0, (Object[])null)).intValue();
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m3 = Class.forName("com.lnjecit.proxy.Subject").getMethod("doSomething");
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
```

可看到

1、代理类继承了Proxy类并且实现了要代理的接口，由于java不支持多继承，所以JDK动态代理不能代理类

2、重写了equals、hashCode、toString

3、有一个静态代码块，`通过反射`获得代理类的所有方法

4、通过invoke执行代理类中的目标方法doSomething


### ASM 框架

ASM库/工具  http://asm.ow2.org/

ASM是一个java字节码操纵框架，它能被用来动态生成类或者增强既有类的功能。ASM 可以直接产生二进制 class 文件，也可以在类被加载入Java
虚拟机之前动态改变类行为。Java class 被存储在严格格式定义的 .class文件里，这些类文件拥有足够的元数据来解析类中的所有元素：类名称、
方法、属性以及 Java 字节码（指令）。ASM从类文件中读入信息后，能够改变类行为，分析类信息，甚至能够根据用户要求生成新类。asm字节码增
强技术主要是用来反射的时候提升性能的，如果单纯用jdk的反射调用，性能是非常低下的，而使用字节码增强技术后反射调用的时间已经基本可以与
直接调用相当了。

ASM字节码处理框架是用Java开发的而且使用基于访问者模式生成字节码及驱动类到字节码的转换，通俗的讲，它就是对class文件的CRUD，经过CRUD
后的字节码可以转换为类。ASM的解析方式类似于SAX解析XML文件，它综合运用了访问者模式、职责链模式、桥接模式等多种设计模式，相对于其他类
似工具如BCEL、SERP、Javassist、CGLIB，它的最大的优势就在于其性能更高，其jar包仅30K。Hibernate和Spring都使用了cglib代理，而cglib
本身就是使用的ASM，可见ASM在各种开源框架都有广泛的应用。

ASM是一个强大的框架，利用它我们可以做到：

- 1、获得class文件的详细信息，包括类名、父类名、接口、成员名、方法名、方法参数名、局部变量名、元数据等
- 2、对class文件进行动态修改，如增加、删除、修改类方法、在某个方法中添加指令等


### CGlib 代理

CGLIB(Code Generation Library)是一个开源项目！是一个强大的，高性能，高质量的Code生成类库，它可以在运行期扩展Java类与实现Java接口。

CGLIB是一个强大的高性能的代码生成包。它广泛的被许多AOP的框架使用，例如Spring AOP为他们提供方法的interception（拦截）。CGLIB包的底层
是通过使用一个小而快的字节码处理框架ASM，来转换字节码并生成新的类。


### AspectJ

AspectJ 是一个面向切面的框架，他定义了 AOP 的一些语法，有一个专门的字节码生成器来生成遵守 java 规范的 class 文件；底层是通过使用ASM。

AspectJ 是Java 语言的一个AOP（面向切面编程）实现，其主要包括两个部分：第一个部分定义了如何表达、定义 AOP 编程中的语法规范，通过这套语言规范，
我们可以方便地用 AOP 来解决 Java 语言中存在的交叉关注点问题；另一个部分是工具部分，包括编译器、调试工具等。

**AspectJ相关jar包**

- aspectjrt.jar：主要是提供运行时的一些注解，静态方法等等东西，通常我们要使用aspectJ的时候都要使用这个包

- aspectjtools.jar：主要是提供赫赫有名的ajc编译器，可以在编译期将将java文件或者class文件或者aspect文件定义的切面织入到业务代码中。
通常这个东西会被封装进各种IDE插件或者自动化插件中。

- aspectjweaverjar：主要是提供了一个java agent用于在类加载期间织入切面(Load time weaving)。并且提供了对切面语法的相关处理等基础
方法，供ajc使用或者供第三方开发使用。这个包一般我们不需要显式引用，除非需要使用LTW。

**aspectJ的几种标准的使用方法**

- 编译时织入，利用ajc编译器替代javac编译器，直接将源文件(java或者aspect文件)编译成class文件并将切面织入进代码。

- 编译后织入，利用ajc编译器向javac编译期编译后的class文件或jar文件织入切面代码。

- 加载时织入，不使用ajc编译器，利用aspectjweaver.jar工具，使用java agent代理在类加载期将切面织入进代码。


### Spring AOP

Spring AOP与AspectJ同样需要对目标类进行增强，也就是生成新的 AOP 代理类；与AspectJ不同的是，Spring AOP无需使用任何特代码进行编译，
它采用运行时动态地、在内存中临时生成“代理类”的方式来生成 AOP 代理。

Spring允许使用AspectJ Annotation用于定义方面（Aspect）、切入点（Pointcut）和增强处理（Advice），Spring框架则可识别并根据这些
Annotation 来生成 AOP 代理。Spring 只是使用了和 AspectJ 一样的注解，但并没有使用 AspectJ 的编译器或者织入器（Weaver），底层
依然使用的是 Spring AOP，依然是在运行时动态生成 AOP 代理，并不依赖于 AspectJ 的编译器或者织入器。

@Aspect来修饰一个Java 类之后，Spring 将不会把该 Bean 当成组件 Bean 处理，因此负责自动增强的后处理 Bean 将会略过该 Bean，不会对该
Bean 进行任何增强处理。

AOP 代理其实是由 AOP 框架动态生成的一个对象，该对象可作为目标对象使用。AOP 代理包含了目标对象的全部方法，但 AOP 代理中的方法与目标
对象的方法存在差异：AOP 方法在特定切入点添加了增强处理，并回调了目标对象的方法。

> Spring AOP配置

1. 为了启用 Spring 对 @AspectJ 方面配置的支持，必须在 Spring 配置文件中配置如下片段：
<aop:aspectj-autoproxy proxy-target-class="true" />

2.高版本的Spring会自动选择是使用`JDK代理`还是`CGLIB代理`。也可以强制使用CGLIB，那就是<aop:config>里面有一个"proxy-target-class"
属性，这个属性值如果被设置为true，那么基于类的代理将起作用，如果proxy-target-class被设置为false或者被忽略，那么基于接口的代理将起作用。 


### [Spring AOP 实现原理与 CGLIB 应用](https://docs.qq.com/doc/DWnNIRWRqQWJ5Y3Fj)

AOP（Aspect Orient Programming），作为面向对象编程的一种补充，广泛应用于处理一些具有横切性质的系统级服务，如事务管理、安全检查、缓存、对象池管理等。
AOP 实现的关键就在于 AOP 框架自动创建的 AOP 代理，AOP 代理则可分为 静态代理 和 动态代理 两大类，其中静态代理是指使用 AOP 框架提供的命令进行编译，
从而在编译阶段就可生成 AOP 代理类，因此也称为 编译时增强；而动态代理则在运行时借助于 JDK 动态代理、CGLIB 等在内存中“临时”生成 AOP 动态代理类，
因此也被称为 运行时增强。


### AOP 底层技术比较

```
AOP底层技术	        功能	        面向接口编程     性能	                
直接改写class文件	完全控制类       高不要求         无明显性能代价	        
JDK Instrument	    完全控制类	    不要求           无论是否改写，每个类装入时都要执行 hook 程序	    
JDK Proxy	        只能改写method	要求            反射引入性能代价	        
ASM	                几乎能完全控制类	不要求          无明显性能代价	        
```


### 动态加载class文件

Java的com.sun.tools.attach包中的VirtualMachine类，该类允许我们通过给attach方法传入一个jvm的pid（进程id），远程连接到jvm上。
然后我们可以通过loadAgent方法向jvm注册一个代理程序agent，在该agent的代理程序中会得到一个Instrumentation实例，该实例可以在class
加载前改变class的字节码，可以在class加载后重新加载。在调用Instrumentation实例的方法时，这些方法会使用ClassFileTransformer接口
中提供的方法进行处理。

想要在jvm启动后，动态的加载class类文件，我们首先需要了解Instrumentation、Attach、Agent、VirtualMachine、ClassFileTransformer
这几个类的用法和他们之间的关系。

> 一、VirtualMachine

VirtualMachine 详细API可以在这里查看：
http://docs.oracle.com/javase/6/docs/jdk/api/attach/spec/com/sun/tools/attach/VirtualMachine.html

VirtualMachine中的attach(String id)方法允许我们通过jvm的pid，远程连接到jvm。当通过Attach API连接到JVM的进程上后，系统会加载
management-agent.jar，然后在JVM中启动一个Jmx代理，最后通过Jmx连接到虚拟机。

**示例代码**

```
// 被监控jvm的pid(windows上可以通过任务管理器查看)  
String targetVmPid = "5936";  
// Attach到被监控的JVM进程上  
VirtualMachine virtualmachine = VirtualMachine.attach(targetVmPid);  

// 让JVM加载jmx Agent  
String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");  
String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";  
virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");  

// 获得连接地址  
Properties properties = virtualmachine.getAgentProperties();  
String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");  

// Detach  
virtualmachine.detach();  
// 通过jxm address来获取RuntimeMXBean对象，从而得到虚拟机运行时相关信息  
JMXServiceURL url = new JMXServiceURL(address);  
JMXConnector connector = JMXConnectorFactory.connect(url);  
RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), "java.lang:type=Runtime",  
        RuntimeMXBean.class);  
// 得到目标虚拟机占用cpu时间  
System.out.println(rmxb.getUptime());  
```

> 二、Agent类

目前Agent类的启动有两种方式，一种是在JDK5版本中提供随JVM启动的Agent，我们称之为premain方式。另一种是在JDK6中在JDK5的基础之上
又提供了JVM启动之后通过Attach去加载的Agent类，我们称之为agentmain方式。

Agent类的两种实现方式：

- 1、随JVM启动的Agent方式：

```
// 必须实现下面两个方法中的其中一个，JVM 首先尝试在代理类上调用以下方法，如果没有，就调用下一个。
public static void premain(String agentArgs, Instrumentation inst);[1]  
public static void premain(String agentArgs);[2]  
```

**premain启动方式**

在jvm的启动参数中加入

```
-javaagent:jarpath[=options]  
```

其中：
  1）一个java程序中-javaagent这个参数的个数是没有限制的，所以可以添加任意多个java agent。所有的java agent会按照你定义的顺序执行。
  2）假设MyProgram.jar里面的main函数在MyProgram中。这2个jar包中实现了premain的类分别是MyAgent1, MyAgent2程序执行的顺序将会是：
     MyAgent1.premain -> MyAgent2.premain -> MyProgram.main

- 2、通过Attach去启动Agent类方式

```
// 必须实现下面两个方法中的其中一个，JVM 首先尝试对代理类调用以下方法，如果没有，就调用下一个
public static void agentmain (String agentArgs, Instrumentation inst);[1]   
public static void agentmain (String agentArgs);[2]   
```

代理类必须实现公共静态agentmain方法。系统类加载器（ClassLoader.getSystemClassLoader）必须支持将代理 JAR 文件添加到系统
类路径的机制。代理 JAR 将被添加到系统类路径。系统类路径是通常加载包含应用程序 main 方法的类的类路径。代理类将被加载，JVM 尝
试调用agentmain 方法。

**agentmain启动代理的方式**

先通过VirtualMachine.attach(targetVmPid)连接到虚拟机，然后通过virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");
注册agent代理类。

```
// 被监控jvm的pid(windows上可以通过任务管理器查看)  
String targetVmPid = "5936";  
// Attach到被监控的JVM进程上  
VirtualMachine virtualmachine = VirtualMachine.attach(targetVmPid);  

// 让JVM加载jmx Agent  
String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");  
String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";  
virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");  
```

代理类的方法中的参数中的Instrumentation：

通过参数中的Instrumentation inst，添加自己定义的ClassFileTransformer，来改变class文件。这里自定义的Transformer实现了
transform方法，在该方法中提供了对实际要执行的类的字节码的修改，甚至可以达到执行另外的类方法的地步

> 三、Instrumentation

java.lang.Instrument包是在JDK5引入的，程序员通过修改方法的字节码实现动态修改类代码。在代理类的方法中的参数中，就有
Instrumentation inst实例。通过该实例，我们可以调用Instrumentation提供的各种接口。比如调用inst.getAllLoadedClasses()
得到所有已经加载过的类。调用inst.addTransformer(new SdlTransformer(), true)增加一个可重转换转换器。调用
inst.retransformClasses(Class cls)，向jvm发起重转换请求。

Java Instrutment只提供了JVM TI中非常小的一个功能子集，一个是允许在类加载之前，修改类字节(ClassFileTransformer)(`JDK5`中开始提供，
即使随JVM启动的Agent)，另外一个是在类加载之后，触发JVM重新进行类加载(`JDK6`中开始提供，用于JVM启动之后通过Attach去加载Agent)。这两
个功能表面看起来微不足道，但实际非常强大，`AspectJ AOP`的动态Weaving、Visual VM的性能剖析、JConsole支持Attach到进程上进行监控，都
是通过这种方式来做的。除了这两个功能外，JDK 6中还提供了动态增加BootstrapClassLoader/SystemClassLoader的搜索路径、对Native方法
进行instrutment()

**主要API(java.lang.instrutment)**

1）ClassFileTransformer：定义了类加载前的预处理类，可以在这个类中对要加载的类的字节码做一些处理，譬如进行字节码增强

2）Instrutmentation：增强器，由JVM在入口参数中传递给我们，提供了如下的功能：

- addTransformer/ removeTransformer：注册/删除ClassFileTransformer
- retransformClasses：对于已经加载的类重新进行转换处理，即会触发重新加载类定义，需要注意的是，新加载的类不能修改旧有的类声明，
譬如不能增加属性、不能修改方法声明
- redefineClasses：与如上类似，但不是重新进行转换处理，而是直接把处理结果(bytecode)直接给JVM
- getAllLoadedClasses：获得当前已经加载的Class，可配合retransformClasses使用
- getInitiatedClasses：获得由某个特定的ClassLoader加载的类定义
- getObjectSize：获得一个对象占用的空间，包括其引用的对象
- appendToBootstrapClassLoaderSearch/appendToSystemClassLoaderSearch：增加BootstrapClassLoader/SystemClassLoader的搜索路径
- isNativeMethodPrefixSupported/setNativeMethodPrefix：支持拦截Native Method

> 四、ClassFileTransformer

```
byte[] transform(ClassLoader loader,String className, Class<?> classBeingRedefined,ProtectionDomain protectionDomain, byte[] classfileBuffer)throws IllegalClassFormatException  
```

该接口只定义个一个方法transform，该方法会在加载新class类或者重新加载class类时，调用。例如，
inst.addTransformer(new SdlTransformer(), true)当代码中增加了一个可重转换转换器后，每次类加载之前，就会调用transform方法。
若该方法返回null，则不改变加载的class字节码，若返回一个byte[]数组，则jvm将会用返回的byte[]数组替换掉原先应该加载的字节码。


> 参考资料

- [JDK动态代理实现原理](https://www.cnblogs.com/zuidongfeng/p/8735241.html)

- [关于java字节码框架ASM的学习](https://blog.csdn.net/qq_27376871/article/details/51613066)

- [Spring AOP 实现原理与 CGLIB 应用](https://www.ibm.com/developerworks/cn/java/j-lo-springaopcglib/index.html)

- [Java类动态加载（二）——动态加载class文件](https://www.iteye.com/blog/zheng12tian-1495037)
