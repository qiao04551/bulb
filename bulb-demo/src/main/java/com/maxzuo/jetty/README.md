## Jetty 的使用

Eclipse Jetty® - Web Container & Clients - supports HTTP/2, HTTP/1.1, HTTP/1.0, websocket, servlets, and more

Jetty的口号是：“不要把你的程序部署到Jetty里，而是把Jetty部署到你的程序里”，意味着，你可以把Jetty当成程序的一个HTTP模块放到你的程序里。

### Jetty 核心原理

核心类：org.mortbay.jetty.Server

核心接口：org.mortbay.component.LifeCycle

核心线程池封装：org.mortbay.thread.QueuedThreadPool

核心IO处理类：org.mortbay.jetty.nio.SelectChannelConnector

核心Servlet处理类：org.mortbay.jetty.servlet.ServletHandler

### Maven依赖

```pom.xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>${servlet.version}</version>
</dependency>
<dependency>
    <groupId>com.caucho</groupId>
    <artifactId>hessian</artifactId>
    <version>${hessian.version}</version>
</dependency>
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-server</artifactId>
    <version>${jetty.version}</version>
</dependency>
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-servlet</artifactId>
    <version>${jetty.version}</version>
</dependency>
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-webapp</artifactId>
    <version>${jetty.version}</version>
</dependency>
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-servlets</artifactId>
    <version>${jetty.version}</version>
</dependency>
```

### 参考资料

- https://www.cnblogs.com/yjmyzz/p/jetty-embed-demo.html