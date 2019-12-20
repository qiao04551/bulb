## Apollo配置中心-客户端

> 外网访问部署

-Deureka.instance.ip-address=${指定的IP}

> 基于application.properties的方式

```yaml
# Apollo配置
app.id: SampleApp
apollo.meta: http://127.0.0.1:8080
```

> 基于System Property的方式配置

-Dapp.id=YOUR-APP-ID
-Dapollo.meta=http://config-service-url
-Denv=Local

**补充：**

1.本地模式下，不会拉取配置文件

2.通过Java system property ${env}_meta，如果当前env是dev，那么用户可以配置-Ddev_meta=http://config-service-url

> 示例配置

-DLocal -Dapp.id=SampleApp -Dapollo.meta=http://118.190.141.231:8071