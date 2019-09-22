## BIM-即时通信

### 项目结构介绍

- **im-client**：客户端
- **im-server**：服务端
- **im-router**：路由负载均衡,ZK协调负载均衡
- **im-common**：公共jar

### Route Map

1.使用Netty快速完成产品原型。

2.BIO阻塞模型（Socket 和 ServerSocket）

3.NIO非阻塞模型（Channel 和 Selector，accept事件、read事件）

4.AIO异步通信模型

### 后期展望

> 一起学习RPC（实现RPC）

http://wei-dong.top/2018/08/31/%E4%B8%80%E8%B5%B7%E5%AD%A6RPC(%E5%9B%9B)/

### 待处理

1. @ChannelHandler.Sharable 注解的作用