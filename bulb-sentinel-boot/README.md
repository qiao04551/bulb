## Sentinel使用Demo

#### 1.dubbo应用接入Sentinel控制台

pom.xml依赖

```
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-core</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-transport-simple-http</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-annotation-aspectj</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-dubbo-adapter</artifactId>
</dependency>
```

给应用添加相关的启动参数，启动应用。需要配置的参数有：
```
-Dcsp.sentinel.api.port：客户端的 port，用于上报相关信息（默认为 8719）
-Dcsp.sentinel.dashboard.server：控制台的地址
-Dproject.name：应用名称，会在控制台中显示
```

启动服务提供者
```
-Dcsp.sentinel.api.port=20885 -Dcsp.sentinel.dashboard.server=127.0.0.1:8080 -Dproject.name=bulb-provider
```

启动服务消费者

```
-Dcsp.sentinel.api.port=8081 -Dcsp.sentinel.dashboard.server=127.0.0.1:8080 -Dproject.name=sentinel-boot
```

