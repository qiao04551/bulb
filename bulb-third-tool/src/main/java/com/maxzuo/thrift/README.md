## 跨语言RPC框架Thrift的使用

Apache Thrift是Facebook 实现的一种高效的、支持多种编程语言的远程服务调用的框架。

Thrift是一个软件框架，用来进行可扩展且跨语言的服务的开发。它结合了功能强大的软件堆栈和代码生成引擎，

Thrift是一个驱动层接口，它提供了用于客户端使用多种语言实现的API。
Thrift是个代码生成库，支持的客户端语言包括C++, Java, Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, 
Smalltalk, and OCaml 。它的目标是为了各种流行的语言提供便利的RPC调用机制，而不需要使用那些开销巨大的方式，比如SOAP。

要使用Thrift，就要使用一个语言中立的服务定义文件，描述数据类型和服务接口。这个文件会被用作引擎的输入，编译器生成代码为每种支持的语言生成
RPC客户端代码库。这种静态生成的设计让它非常容易被开发者所使用，而且因为类型验证都发生在编译期而非运行期，所以代码可以很有效率地运行。

Thrift的设计提供了以下这些特性：

1、语言无关的类型

因为类型是使用定义文件按照语言中立的方式规定的，所以它们可以被不同的语言分析。比如，C++的结构可以和Python的字典类型相互交换数据。

2、通用传输接口

不论你使用的是磁盘文件、内存数据还是socket流，都可以使用同一段应用代码。

3、协议无关

Thrift会对数据类型进行编码和解码，可以跨协议使用。

4、支持版本

数据类型可以加入版本信息，来支持客户端API的更新。

### Mac安装Thrift编译器

```
brew install thrift
thrift -version
```

### thrift基本概念

#### 1.数据类型

**基本类型：**
- bool：布尔值，true 或 false，对应 Java 的 boolean
- byte：8 位有符号整数，对应 Java 的 byte
- i16：16 位有符号整数，对应 Java 的 short
- i32：32 位有符号整数，对应 Java 的 int
- i64：64 位有符号整数，对应 Java 的 long
- double：64 位浮点数，对应 Java 的 double
- string：utf-8编码的字符串，对应 Java 的 String

**结构体类型：**
- struct：定义公共的对象，类似于 C 语言中的结构体定义，在 Java 中是一个 JavaBean

**容器类型：**
- list：对应 Java 的 ArrayList
- set：对应 Java 的 HashSet
- map：对应 Java 的 HashMap

**异常类型：**
- exception：对应 Java 的 Exception

**服务类型：**
- service：对应服务的类

#### 2.服务端编码基本步骤

- 实现服务处理接口impl
- 创建TProcessor
- 创建TServerTransport
- 创建TProtocol
- 创建TServer
- 启动Server

#### 3.客户端编码基本步骤

- 创建Transport
- 创建TProtocol
- 基于TTransport和TProtocol创建 Client
- 调用Client的相应方法

#### 4.数据传输协议

- TBinaryProtocol : 二进制格式
- TCompactProtocol : 压缩格式
- TJSONProtocol : JSON格式
- TSimpleJSONProtocol : 提供JSON只写协议（缺少元数据信息）, 生成的文件很容易通过脚本语言解析
- TDebugProtocol：使用易懂的刻度文本格式，以便于调试

### 简单的调用示例

1.Thrift生成代码

创建Thrift文件：//Users/dazuo/demoHello.thrift ,内容如下：

```
namespace java com.maxzuo.thrift
 
service  HelloWorldService {
  string sayHello(1:string username)
}
```

使用编译器，生成相关代码

```
thrift --gen <language> <Thrift filename>
```

文件生成在gen-java目录

```
文件内容太长此处忽略...
```

2.创建项目，添加项目依赖

```
<dependency>
  <groupId>org.apache.thrift</groupId>
  <artifactId>libthrift</artifactId>
  <version>0.12.0</version>
</dependency>
```

3.将生成的HelloWorldService.java文件拷贝到项目

4.实现接口Iface

参见：HelloWorldImpl.java

5.TSimpleServer服务端

参见：SimpleServer.java

6.编写客户端Client代码

参见：SimpleClient.java