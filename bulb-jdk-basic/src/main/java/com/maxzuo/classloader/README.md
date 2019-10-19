## 深入理解Java虚拟机

### 类的加载

> **基本概念**

**字节码**

1.Java所有的指令有200个左右，一个字节（8位）可以存储256种不同的指令信息，一个这样的字节被称为字节码（Bytecode）。在代码
执行过程中，JVM将字节码解释执行，屏蔽对底层操作系统的依赖。

2.编写好的.java文件是源代码文件，并不能交给机器直接执行，需要通过javac命令将其编译成为字节码甚至是机器码文件。

**方法区**

1.方法区与Java堆一样，是各个线程共享的内存区域，它用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码
  等数据。

2.对于习惯在HotSpot虚拟机上开发、部署程序的开发者来说，很多人都更愿意把方法区称为“永久代”，本质上两者并不等价，仅仅是因
  为HotSpot虚拟机的设计团队选择把GC分代收集扩展至方法区，或者说使用永久代来实现方法区而已，这样HotSpot的垃圾收集器可以
  像管理Java堆一样管理这部分内存，能够省去专门为方法区编写内存管理代码的工作。对于其他虚拟机（BEA JRockit、IBM J9）来
  说是不存在永久代的概念的，采用Native Memory来实现方法区。

3.JDK8的HotSpot中移除了永久代（PermGen）使用元空间（Metaspace）在本地内存中分配。


**常量池**

1.class文件常量池是在编译的时候每个class都有的，在编译阶段，存放的是常量的符号引用，包含类的版本、字段、方法接口等描述信息。

2.运行时常量池。用于存放编译期生成的各种字面量和符号引用，这部分内容将在类加载后进入运行常量池。

3.字符串常量池。在JDK8里，Perm区中的所有内容中的字符串常量移至堆内存，其他内容包括类元信息、字段、静态属性、方法、常量等
都移动到元空间内。

> **虚拟机类的加载机制**

1.虚拟机吧描述类的数据从class文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可以被虚拟机加载直接使用的Java
  类型，这就是虚拟机的类加载机制。

2.与那些编译时需要进行连接工作的语言不同，在Java语言里面，类型的加载、连接和初始化都是在程序运行期间完成的，这种策略虽然
  会令类加载时稍微增加一些性能开销，但是会为Java应用程序提供高度的灵活性。
	

> **类加载的时机**

1.类从被加载到虚拟机内存中开始，到卸载出内存为止，它的整个生命周期包括：加载、验证、准备、解析、初始化、使用和卸载7个阶段。
其中验证、准备、解析3个部分统称为连接。

2.加载、验证、准备、初始化和卸载这5个阶段的顺序是确定的，类的加载过程过程必须按照这种顺序按部就班地开始，而解析阶段就不一定：
它在某些情况下可以在初始化之后再开始，这是为了支持Java语言的运行时绑定。（也称为动态绑定）

3.什么时候开始类加载过程的第一阶段：加载？Java虚拟机规范中并没有强制约束，这点可以交给虚拟机的具体实现来自由把握。

4.对于初始化阶段，虚拟机规范则是严格规定了有且只有5种情况必须立即对类进行初始化（而加载、验证、准备自然需要在此之前开始）：

1）遇到new、getstatic、putstatic或invokestatic这4条字节码指令时。

2）使用java.lang.reflect包的方法对类进行反射调用的时候。

3）当初始化一个类的时候，发现其父类还没有初始化，则需要先触发其父类的初始化。

4）当虚拟机启动的时候，用户需要指定一个要执行的主类。虚拟机会先初始化这个主类。

5）当使用JDK1.7的动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、
REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。

其中：

这5种场景中的行为称为对一个类进行主动引用。除此之外，所有引用类的方式都不会触发初始化，称为被动引用。

> **类的加载过程**

1.“加载”是“类加载”过程的一个阶段，在这个阶段虚拟机需要完成以下3件事：

    1）通过类的全限定名来获取定义此类的二进制字节流。
    2）将这个字节流所代表的静态存储结果转化为“元空间”的运行时数据结构。
    3）在内存中生成一个代表这个类的java.lang.Class对象，作为“元空间”这个类的各种数据的访问入口。
  
2.验证是连接的第一步，大致完成四个阶段的检验动作：文件格式验证、元数据验证、字节码验证、符号引用验证。

3.准备是正式为类变量分配内存并设置类变量初始值的阶段。这时候变量都在元空间中进行分配，这时候进行内存分配的仅包括类变量
（被static修饰的变量），而不包含实例变量，实例变量将会在对象实例化时随着对象一起分配在Java堆中。

4.解析是虚拟机将常量池内的符号引用替换为直接引用的过程：

    1）符号引用：符号引用以一组符号来描述所引用的目标，符号可以是任何形式的字面量。符号引用与虚拟机实现的内存布局无关，
       引用的目标不一定加载到内存中。

    2）直接引用：直接引用可以是直接指向目标的指针、相对偏移量或是一个能间接定位到目标的句柄。直接引用是和虚拟机实现的
       内存布局相关的。

    3）类和接口的解析：
        假设当前代码所处的类为D，如果要把一个从未解析过的符号引用N解析为一个类或接口C的直接引用，那么会执行三个步骤：
    
        1.如果C不是一个数组类型，那虚拟机会把代表N的全限定名传递给D的类加载器去加载这个类C。在加载过程中，由于元数据验证、
          字节码验证的需要，又可能触发其它相关类的加载动作。
    
        2.如果C是一个数组类型，并且数组的元素类型为对象，那么会按照步骤 1的规则加载数组元素的类型，接着由虚拟机生成一个代
          表此数组维度和元素的数组对象。
    
        3.如果上面的步骤没有出现任何异常，那么C在虚拟机中实际上已成为一个有效的类或接口了。

    4）字段解析、类方法解析、接口方法解析，《深入理解Java虚拟机》P222

5.初始化

    类初始化阶段是类加载过程中的最后一步，前面的类加载过程中，除了在加载阶段用户应用程序可以通过自定义类加载器参与之外，
    其余动作完全由虚拟机主导和控制。到了初始化阶段，才真正之开始执行类定义的Java程序代码（或者说是字节码）。初始化阶段
    是执行类构造器<clinit>()方法的过程。<clinit>方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块（static{}块）
    中的语句合并产生的，编译器收集的顺序是由语句在源文件中出现的顺序决定的。

### 类加载器

> 基本概念

1.虚拟机设计团队把类加载阶段中的“通过一个类的全限定名来获取描述此类的二进制字节流”这个动作放到Java虚拟机外部去实现，
  以便让应用程序自己决定如何去获取所需要的类。实现这个动作的模块称为“类加载器”。

2.类的加载器虽然只用于实现类的加载动作，但它在Java程序中起到的作用却远远不限于类的加载阶段。对于任意一个类，都需要由
  加载它的类加载器和这个类本身一同确定其在Java虚拟机中的唯一性，每一个类加载器，都拥有一个独立的类名称空间。这句话可
  以表达通俗一点：比较两个类是否“相等”，只有在这个类是由同一个类加载器加载的前提下才有意义，否则，即使这个两个类来源
  于同一个Class文件，被同一个虚拟机加载，只要加载它们的类加载器不同，那么这两个类依然是两个独立的类，就必定不会相等。


> 系统提供的类加载器

**启动类加载器（Bootstrap ClassLoader）**

负责将存放在<JAVA_HOME>\lib目录中的，或者被-Xbootclasspath参数所指定的路径中的，并且被虚拟机识别的（仅按照文件名识别，
如：rt.jar；名字不符合的类库即使放在lib目录中也不会被加载）类库加载到虚拟机内存中。

启动类加载器无法被java程序直接引用，用户在编写自定义类加载器时，如果需要把加载请求委派给引导类加载器，那直接使用null
代替即可。

**扩展类加载器（Extension ClassLoader）**

这个类加载器由sum.misc.Launcher$ExtClassLoader实现，它负责加载<JAVA_HOME>\lib\ext目录中的，或者被java.ext.dirs
系统变量所指定的路径中的所有类库，开发者可以直接使用扩展类加载器。

**应用程序类加载器（Application ClassLoader）**

这个类加载器由sun.misc.Launcher$AppClassLoader来实现。负责加载用户类路径classpath上指定的类库。开发者可以直接
使用这个类加载器，如果应用程序中没有自定义过自己的类加载器，一般情况下这个就是程序中默认的类加载器。


> 自定义类加载器场景

1.隔离加载类。在某些框架内进行中间件与应用的模块隔离，把类加载到不同的环境。

2.修改类加载方式。类的加载模型并非强制，除Bootstrap外，其他的加载并非一定要引入或者根据实际情况在某个时间点进行按需
进行动态加载。

3.扩展加载源。比如从数据库、网络进行加载。

4.防止源码泄漏。Java代码容易被编译和篡改，可以进行编译加密。那么加载器也需要自定义，还原加密的字节码。

5.自定义ClassLoader

    1）自定义类加载器，只需要继承ClassLoader类，复写findClass方法，在findClass方法中调用defineClass方法即可。
    
    2）ClassLoader类的重要接口方法：
       Class loadClass(String name, boolean resolve);
       name参数：指定类装载器需要装载类的名字，必须使用全限定类名。
       resolve参数：默认值为false；如果为true调用resolveClass()方法处理该类。如果调用defineClass()生成类的Class
                   对象，这个类的Class对象还没有resolve，这个resolve将会在这个对象真正实例化时才进行。
       Class defineClass(String name, byte[] b, int off, int len);
    　 将类文件的字节数组转换成JVM内部的java.lang.Class对象。字节数组可以从本地文件系统、远程网络获取。name为字节数
       组对应的全限定类名。	
    
    3）ClassLoader只能加载classpath下面的类，而URLClassLoader可以加载任意路径下的类，比如：文件: (从文件系统目录
       加载)、jar包: (从Jar包进行加载)、Http: (从远程的Http服务进行加载)


> 双亲委派模型

1.类加载器之间的这种层次关系，被称为类加载器的双亲委派模型。双亲委派模型要求除了顶层的启动类加载器外，其余的类加载器都
  应当有自己的父类加载器。这里类加载器之间的父子关系不会以继承的关系来实现，而是都使用组合关系来复用父加载器的代码。

2.双亲委派模型的工作流程：如果一个类加载器收到类加载的请求，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加
  载器去完成，每一个层次的类加载器都是如此，因此所有的加载请求最终都应该传送到顶层的启动类加载器中，只有当父加载器反馈
  自己无法完成加载请求（它的搜索范围中没有找到所需的类）时，子加载器才会尝试自己去加载。


### 补充

1.对象实例化（执行的角度）

    1）确认类的元信息是否存在。当JVM接收到new指令时，首先在metaspace内检查需要创建的类元信息是否存在。若不存在，那么
      在双亲委派模式下，使用当前类加载器以ClassLoader+包名+类名为Key进行查找对应的.class文件。如果没有找到该文件，
      则抛出ClassNotFoundException异常；如果找到，则进行类加载，并生成对应的Class类对象。
    2）分配对象内存。
    3）设置默认值。成员变量都需要设定默认值，即各种不同形式的零值。
    4）设置对象头。设置新对象的哈希码、GC信息、锁信息、对象所属的类元信息等。
    5）执行init。初始化成员方法变量，执行实例化代码块，调用类的构造方法，并把堆内对象的首地址赋值给引用变量。

