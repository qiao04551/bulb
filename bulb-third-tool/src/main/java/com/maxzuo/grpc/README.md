## GRPC 

定义一个服务，指定其可以被远程调用的方法及其参数和返回类型。gRPC 默认使用 protocol buffers 作为接口定义语言，来描述服务接口和有效载荷消息结构。


### 入门示例

**编写proto文件**

```proto
syntax = "proto3";

option java_package = "com.maxzuo.grpc.protocol";
option java_outer_classname = "NetworkProtocol";
option java_multiple_files = true;

service SearchService {
    rpc search (SearchRequest) returns (SearchResponse);
}

message SearchRequest {
    string greeting = 1;
}

message SearchResponse {
    string reply = 1;
}
```

**maven依赖和编译配置**

```
<dependency>
    <groupId>com.google.protobuf</groupId>
    <artifactId>protobuf-java</artifactId>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-all</artifactId>
</dependency>

<build>
    <extensions>
        <extension>
            <groupId>kr.motd.maven</groupId>
            <artifactId>os-maven-plugin</artifactId>
            <version>1.6.2</version>
        </extension>
    </extensions>
    <plugins>
        <plugin>
            <groupId>org.xolstice.maven.plugins</groupId>
            <artifactId>protobuf-maven-plugin</artifactId>
            <version>0.6.1</version>
            <configuration>
                <protocArtifact>com.google.protobuf:protoc:3.9.0:exe:${os.detected.classifier}</protocArtifact>
                <pluginId>grpc-java</pluginId>
                <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.23.0:exe:${os.detected.classifier}</pluginArtifact>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                        <goal>compile-custom</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

**编译proto文件，生成java代码**

```
# 注意：proto文件夹与java文件夹处于同级目录
$ mvn compile
```