## zxcity-gateway

基于SpringCloud Gateway构建业务网关


### SpringCloud Gateway简介

SpringCloud Gateway 是 Spring Cloud 的一个全新项目，该项目是基于 Spring 5.0，Spring Boot 2.0 和 Project Reactor 
等技术开发的网关，它旨在为微服务架构提供一种简单有效的统一的 API 路由管理方式。

SpringCloud Gateway 作为 Spring Cloud 生态系统中的网关，目标是替代 Zuul，在Spring Cloud 2.0以上版本中，没有对新版本的
Zuul 2.0以上最新高性能版本进行集成，仍然还是使用的Zuul 2.0之前的非Reactor模式的老版本。而为了提升网关的性能，SpringCloud Gateway
是基于WebFlux框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty。

Spring Cloud Gateway 的目标，不仅提供统一的路由方式，并且基于 Filter 链的方式提供了网关基本的功能，例如：安全，监控/指标，和限流。

提前声明：Spring Cloud Gateway 底层使用了高性能的通信框架Netty。


### SpringCloud Gateway特征

1）基于 Spring Framework 5，Project Reactor 和 Spring Boot 2.0
2）集成 Hystrix 断路器
3）集成 Spring Cloud DiscoveryClient
4）Predicates 和 Filters 作用于特定路由，易于编写的 Predicates 和 Filters
5）具备一些网关的高级功能：动态路由、限流、路径重写

> 术语（Glossary）

- Route（路由）

定义：网关配置的基本组成模块，和Zuul的路由配置模块类似。一个Route模块由一个 ID，一个目标 URI，一组断言和一组过滤器定义。如果断言为真，
则路由匹配，目标URI会被访问。

- Predicate（断言）

这是一个Java 8函数断言。输入类型是一个Spring框架ServerWebExchange。这使您可以匹配来自HTTP请求的任何内容，例如头或参数。

- Filter（过滤器）

这些是由特定工厂构造的Spring Framework网关过滤器实例。在这里，您可以在发送下游请求之前或之后修改请求和响应。


### SpringCloud Gateway的处理流程

客户端向 Spring Cloud Gateway 发出请求。然后在 Gateway Handler Mapping 中找到与请求相匹配的路由，将其发送到 Gateway Web Handler。
Handler 再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回。过滤器之间用虚线分开是因为过滤器可能会在发送代理请求
之前（“pre”）或之后（“post”）执行业务逻辑。

![architecture](./doc/architecture.jpeg)


### SpringCloud Gateway匹配规则

Spring Cloud Gateway 的功能很强大，我们仅仅通过 Predicates 的设计就可以看出来，前面我们只是使用了 predicates 进行了简单的条件
匹配，其实 Spring Cloud Gataway 帮我们内置了很多 Predicates 功能。

Spring Cloud Gateway 是通过 Spring WebFlux 的 HandlerMapping 做为底层支持来匹配到转发路由，Spring Cloud Gateway 内置了
很多Predicates 工厂，这些 Predicates 工厂通过不同的 HTTP 请求参数来匹配，多个 Predicates 工厂可以组合使用。

> Predicate 断言条件介绍

Predicate 来源于 Java 8，是 Java 8 中引入的一个函数，Predicate 接受一个输入参数，返回一个布尔值结果。该接口包含多种默认方法来将 
Predicate 组合成其他复杂的逻辑（比如：与，或，非）。可以用于接口请求参数校验、判断新老数据是否有变化需要进行更新操作。

在 Spring Cloud Gateway 中 Spring 利用 Predicate 的特性实现了各种路由匹配规则，有通过 Header、请求参数等不同的条件来进行作为
条件匹配到对应的路由。

![predicate](./doc/predicate.gif)


### SpringCloud Gateway过滤器

sprincloud gateway中主要有两种类型的过滤器：GlobalFilter 和 GatewayFilter

- GlobalFilter： 全局过滤器，对所有的路由均起作用
- GatewayFilter： 作用域是指定的路由

> Filter的作用和生命周期

在“pre”类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等，在“post”类型的过滤器中可以做响应内容、响应头的修改，日志
的输出，流量监控等。

Spring Cloud Gateway同zuul类似，有“pre”和“post”两种方式的filter。客户端的请求先经过“pre”类型的filter，然后将请求转发到具体的
业务服务，收到业务服务的响应之后，再经过“post”类型的filter处理，最后返回响应到客户端。

- Pre类型

在Spring Cloud Gateway源码中定义了一个Pre类型的Filter，code将会在chain.filter() 之前被执行。示例：AddRequestHeaderGatewayFilterFactory

- Post类型

对于Post类型的Filter，代码将会在chain.filter(exchange).then()里面的代码运行。示例：SetStatusGatewayFilterFactory


### 参考资料

1.[SpringCloud gateway （史上最全）](https://www.cnblogs.com/crazymakercircle/p/11704077.html)
2.[spring cloud gateway之filter篇](https://www.cnblogs.com/forezp/archive/2018/12/18/10135714.html)
3.[Spring Cloud Gateway只有Pre和POST两种类型的Filter](http://springcloud.cn/view/265)


