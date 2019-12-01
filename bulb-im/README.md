## BIM-即时通信

IM即时聊天室，多种实现方式（BIO、NIO、Netty）

- 1.使用Netty快速完成聊天室原型

- 2.使用BIO阻塞模型重构聊天室（Socket 和 ServerSocket）

- 3.使用NIO非阻塞模型重构聊天室

- 4.AIO异步通信模型

### 项目结构

- im-bio 基于BIO的聊天室
- im-nio 基于NIO的聊天室
- im-client 基于netty的im客户端
- im-server 基于netty的im服务端
- im-common 基于netty的im公共模块

### Road Map

1.im-router 路由负载均衡,ZK协调负载均衡

2.粘包、断包的问题

3.Google Protobuf编码解码