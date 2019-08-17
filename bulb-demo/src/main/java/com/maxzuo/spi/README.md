## SPI 机制

SPI的全名为Service Provider Interface，服务提供发现，动态替换发现的机制。
在java.util.ServiceLoader的文档:https://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html中有比较详细的介绍。

在我们日常开发的时候都是对问题进行抽象成Api然后就提供各种Api的实现，这些Api的实现都是封装与我们的Jar中或框架中的虽然当我们想要提供一种
Api新实现时可以不修改原来代码只需实现该Api就可以提供Api的新实现，但我们还是生成新Jar或框架（虽然可以通过在代码里扫描某个目录已加载Api
的新实现，但这不是Java的机制，只是hack方法），而通过Java SPI机制我们就可以在不修改Jar包或框架的时候为Api提供新实现。

### SPI机制的约定

- 1）在META-INF/services/目录中创建以接口全限定名命名的文件，该文件内容为Api具体实现类的全限定名

- 2）使用java.util.ServiceLoader类动态加载META-INF中的实现类

- 3）如SPI的实现类为Jar则需要放在主程序classPath中

- 4）Api具体实现类必须有一个不带参数的构造方法

### SPI应用场景举例

**JDBC**

jdbc4.0以前， 开发人员还需要基于Class.forName("xxx")的方式来装载驱动，jdbc4也基于spi的机制来发现驱动提供商了，可以通过
META-INF/services/java.sql.Driver文件里指定实现类的方式来暴露驱动提供者.

参见：java.sql.DriverManager.loadInitialDrivers

**COMMON-LOGGING**

apache最早提供的日志的门面接口。只有接口，没有实现。具体方案由各提供商实现， 发现日志提供商是通过扫描METAINF/services/org.apache.commons.logging.LogFactory
配置文件，通过读取该文件的内容找到日志提工商实现类。只要我们的日志实现里包含了这个文件，并在文件里制定 LogFactory工厂接口的实现类即可。