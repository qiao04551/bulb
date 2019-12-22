## SofaBoot

版本：3.1.4

> 健康检查

Readiness Check：http://localhost:8080/actuator/readiness

健康状态：http://localhost:8080/actuator/health

版本信息：http://localhost:8080/actuator/versions

> 日志配置

```yaml
# 日志配置
logging:
  path: ./logs
  file: ./logs/boot.log
  level:
    root: INFO
  pattern:
    console: '%d [%t] %-5p [%c] - %m%n'
    file: '%d [%t] %-5p [%c] - %m%n'
```

> SofaLockout

功能不齐全，开发中


> SOFATracer

功能不齐全，开发中

> SofaRPC

服务提供者，可以绑定多个协议；消费者只能绑定一个协议

SOFARPC 的 RESTful 服务的默认端口为 8341。示例：http://localhost:8341/webapi/sayName/123/232

JAXRS 的标准的注解的使用方式可以参考[RESTEasy 的文档](https://docs.jboss.org/resteasy/docs/3.0.12.Final/userguide/html/)
