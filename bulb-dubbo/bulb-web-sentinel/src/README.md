## 2019-07-12 Sentinel 集成限流、降级

- 1.使用AOP进行Controller接口资源定义，com.maxzuo.protect.SentinelRestControllerProtect
- 2.使用 sentinel-dubbo-adapter 依赖进行dubbo资源定义。
- 3.使用zookeeper定义动态规则，com.maxzuo.protect.SentinelZookeeperRules
- 4.增强Sentinel-core包（1.6.1.kafka），将原始的Metric信息输出到文件，转向输出到Kafka。
- 5.使用APM-colletor进行采集Metric信息。（由参数区分 环境、服务）
- 6.使用APM-dashboard进行数据展示，限流规则动态配置。