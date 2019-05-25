### Spring-Data mongoDB 示例
- 没有继承父项目bulb-parent，有依赖冲突
```
<!--修改springBoot默认父POM-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>${spring-boot.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```