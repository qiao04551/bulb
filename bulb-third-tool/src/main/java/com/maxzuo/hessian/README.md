## Hessian的binary-RPC框架的使用

Hessian 是由 caucho 提供的一个基于 binary-RPC 实现的远程通讯 library。它基于HTTP协议进行传输，使用Hessian二进制序列化，对于
数据包比较大的情况比较友好。

**通信过程**

- 1.客户端通过 Hessian 提供的 API 来发起请求（通过其自定义的串行化机制将请求信息进行序列化，产生二进制流。完全使用动态代理来实现）
- 2.Hessian 基于 Http 协议进行传输。
- 3.服务端接收到请求，通过Hessian提供的API来接收请求（根据其私有的串行化机制来将请求信息进行反序列化，传递给使用者时已是相应的请求信息对象了）
- 4.处理完毕后直接返回，hessian 将结果对象进行序列化，传输至调用端。

**Hessian 的序列化和反序列化实现**

hessian 源码中 com.caucho.hessian.io 这个包是 hessian 实现序列化与反序列化的核心包。其中 AbstractSerializerFactory，
AbstractHessianOutput ， AbstractSerializer ， AbstractHessianInput ， AbstractDeserializer 是 hessian 实现序
列化和反序列化的核心结构代码。

### Servlet-服务端

**web.xml 配置**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--声明 HessianServlet-->
    <servlet>
        <servlet-name>hessianServlet</servlet-name>
        <servlet-class>com.caucho.hessian.server.HessianServlet</servlet-class>
        <init-param>
            <param-name>service-class</param-name>
            <param-value>com.maxzuo.bulb.hessian.service.BasicImpl</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>hessianServlet</servlet-name>
        <url-pattern>/hessian</url-pattern>
    </servlet-mapping>
</web-app>
```
