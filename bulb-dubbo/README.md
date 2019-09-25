## dubbo

主要介绍dubbo RPC框架在项目中的结构

### 项目结构

- common            公共模块（API、Model、Exception）
- shop-provider     服务提供者
- bulb-web-sentinel Web服务（服务消费者）+ 哨兵流控

### 接入探针

```
-javaagent:/Users/dazuo/workplace/zxcity-apm/apm-sniffer/apm-agent/target/apm-agent-1.0.2.jar -DserviceName=bulb-web
```