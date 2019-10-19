## Mybatis

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动
设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO
（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

### 核心概念

> **SqlSessionFactory**

每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。SqlSessionFactory 的实例可以通过
SqlSessionFactoryBuilder 获得。而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先定制的 Configuration
的实例构建出 SqlSessionFactory 的实例。

> **SqlSession**

既然有了 SqlSessionFactory，顾名思义，我们就可以从中获得 SqlSession 的实例了。SqlSession 完全包含了面向数据库执行
SQL 命令所需的所有方法。你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。

> **作用域（Scope）和生命周期**

理解我们目前已经讨论过的不同作用域和生命周期类是至关重要的，因为错误的使用会导致非常严重的并发问题。

**SqlSessionFactoryBuilder**

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的
最佳作用域是方法作用域（也就是局部方法变量）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，
但是最好还是不要让其一直存在，以保证所有的 XML 解析资源可以被释放给更重要的事情。

**SqlSessionFactory**

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 
的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏味道（bad smell）”。因此 
SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

**SqlSession**

每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是
请求或方法作用域。 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 也绝不能将 SqlSession 
实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 如果你现在正在使用一种 Web 框架，要考虑 SqlSession 
放在一个和 HTTP 请求对象相似的作用域中。 换句话说，每次收到的 HTTP 请求，就可以打开一个 SqlSession，返回一个响应，就关闭它。
这个关闭操作是很重要的，你应该把这个关闭操作放到 finally 块中以确保每次都能执行关闭。

**映射器实例**

映射器是一些由你创建的、绑定你映射的语句的接口。映射器接口的实例是从 SqlSession 中获得的。因此从技术层面讲，任何映射器
实例的最大作用域是和请求它们的 SqlSession 相同的。尽管如此，映射器实例的最佳作用域是方法作用域。 也就是说，映射器实例
应该在调用它们的方法中被请求，用过之后即可丢弃。 并不需要显式地关闭映射器实例，尽管在整个请求作用域保持映射器实例也不会
有什么问题，但是你很快会发现，像 SqlSession 一样，在这个作用域上管理太多的资源的话会难于控制。 为了避免这种复杂性，最好
把映射器放在方法作用域内。


### 缓存

> **1.一级缓存**

Mybatis对缓存提供支持，但是在没有配置的默认情况下，它只开启一级缓存，一级缓存只是相对于同一个SqlSession而言。所以在参数
和SQL完全一样的情况下，我们使用同一个SqlSession对象调用一个Mapper方法，往往只执行一次SQL，因为使用SelSession第一次查询后，
MyBatis会将其放在缓存中，以后再查询的时候，如果没有声明需要刷新，并且缓存没有超时的情况下，SqlSession都会取出当前缓存的
数据，而不会再次发送SQL到数据库。

**一级缓存的生命周期有多长？**

1）MyBatis在开启一个数据库会话时，会 创建一个新的SqlSession对象，SqlSession对象中会有一个新的Executor对象。Executor
   对象中持有一个新的PerpetualCache对象；当会话结束时，SqlSession对象及其内部的Executor对象还有PerpetualCache对象也
   一并释放掉。

2）如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用。

3）如果SqlSession调用了clearCache()，会清空PerpetualCache对象中的数据，但是该对象仍可使用。

4）SqlSession中执行了任何一个update操作(update()、delete()、insert()) ，都会清空PerpetualCache对象的数据，但是该
   对象可以继续使用。

**一级缓存配置**

```
# 指定select是否开启一级缓存，可以通过设置useCache来规定这个sql是否开启缓存，ture是开启，false是关闭
<select id="selectAllStudents" resultMap="studentMap" useCache="true">
    SELECT id, name, age FROM student
</select>
```

> **2.二级缓存**

MyBatis的二级缓存是Application级别的缓存，它可以提高对数据库查询的效率，以提高应用的性能。

SqlSessionFactory层面上的二级缓存默认是不开启的，二级缓存的开启需要进行配置，实现二级缓存的时候，MyBatis要求返回的POJO
必须是可序列化的。 也就是要求实现Serializable接口，配置方法很简单，只需要在映射XML文件配置就可以开启缓存了<cache/>，如果
我们配置了二级缓存就意味着：

1）映射语句文件中的所有select语句将会被缓存。

2）对于缓存数据更新机制，当某一个作用域（一级缓存Session/二级缓存Namespaces）的进行了C/U/D操作后，默认该作用域下所有
   select中的缓存将被clear。

3）缓存会使用默认的Least Recently Used（LRU，最近最少使用的）算法来收回。

4）根据时间表，比如No Flush Interval,（CNFI没有刷新间隔），缓存不会以任何时间顺序来刷新。

5）缓存会存储列表集合或对象(无论查询方法返回什么)的1024个引用
   缓存会被视为是read/write(可读/可写)的缓存，意味着对象检索不是共享的，而且可以安全的被调用者修改，不干扰其他调用者或
   线程所做的潜在修改。

**二级缓存配置**

`<cache eviction="LRU" flushInterval="100000" readOnly="true" size="1024"/>`

其中：

   eviction:代表的是缓存回收策略，目前MyBatis提供以下策略。
   
   1）LRU，最近最少使用的，一处最长时间不用的对象
   
   2）FIFO，先进先出，按对象进入缓存的顺序来移除他们
   
   3）SOFT，软引用，移除基于垃圾回收器状态和软引用规则的对象
   
   4）WEAK，弱引用，更积极的移除基于垃圾收集器状态和弱引用规则的对象。这里采用的是LRU，移除最长时间不用的对形象

   flushInterval：刷新间隔时间，单位为毫秒，这里配置的是100秒刷新，如果你不配置它，那么当SQL被执行的时候才会去刷新缓存。
   
   size：引用数目，一个正整数，代表缓存最多可以存储多少个对象，不宜设置过大。设置过大会导致内存溢出。这里配置的是1024个对象
   
   readOnly：只读，意味着缓存数据只能读取而不能修改，这样设置的好处是我们可以快速读取缓存，缺点是我们没有办法修改缓存，他的
   默认值是false，不允许我们修改

### Mybatis-Spring

MyBatis-Spring 会帮助你将 MyBatis 代码无缝地整合到 Spring 中。它将允许 MyBatis 参与到 Spring 的事务管理之中，创建映射器
mapper 和 SqlSession 并注入到 bean 中，以及将 Mybatis 的异常转换为 Spring 的 DataAccessException。最终，可以做到应用
代码不依赖于 MyBatis，Spring 或 MyBatis-Spring。
 

 

### 参考文献

1.[Mybatis官方文档](https://mybatis.org/mybatis-3/zh/getting-started.html)

2.[Mybatis-Spring官方文档](http://mybatis.org/spring/zh/index.html)

3.[深入浅出Mybatis系列（十）---SQL执行流程分析（源码篇）](https://www.cnblogs.com/dongying/p/4142476.html)