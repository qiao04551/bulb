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

