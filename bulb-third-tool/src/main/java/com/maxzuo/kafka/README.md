## Kafka 

Kafka是由Apache软件基金会开发的一个开源流处理平台，由Scala和Java编写。Kafka是一种高吞吐量的分布式发布订阅消息系统，它可以处理
消费者在网站中的所有动作流数据。 这种动作（网页浏览，搜索和其他用户的行动）是在现代网络上的许多社会功能的一个关键因素。 这些数据
通常是由于吞吐量的要求而通过处理日志和日志聚合来解决。 


### Kafka的特性

- 发布 & 订阅：数据流，如消息传递系统
- 处理：高效并实时
- 存储：数据流安全地在分布式集群中复制存储

> **Kafka作为一个分布式的流平台**

我们认为，一个流处理平台具有三个关键能力：

1.发布和订阅消息(流)，在这方面，它类似于一个消息队列或企业消息系统。

2.以容错(故障转移)的方式存储消息(流)。

3.在消息流发生时处理它们。

> **kafka的优势，主要应用于2大类应用：**

1.构建实时的流数据管道，可靠地获取系统和应用程序之间的数据。

2.构建实时流的应用程序，对数据流进行转换或反应。


### Kafka的基本术语

- **Topic**：Kafka将消息种子(Feed)分门别类，每一类的消息称之为一个主题(Topic)。

- **Broker**：已发布的消息保存在一组服务器中，称之为Kafka集群。集群中的每一个服务器都是一个代理(Broker)。 消费者可以订阅一个或
多个主题（topic），并从Broker`拉数据`，从而消费这些已发布的消息。

- **Producer**：发布消息的对象称之为主题生产者(Kafka topic producer)

- **Comsumer**：订阅消息并处理发布的消息的种子的对象称之为主题消费者（consumers）


### 安装Kafka（kafka_2.11-0.10.0.0）

1.官网https://kafka.apache.org/downloads下载Kafka。

2.启动自带的zookeeper，读写性能高：
```
$ bin/zookeeper-server-start.sh config/zookeeper.properties > logs/zookeeper-server.log 2>&1 &
```

3.启动kafka服务器（默认端口：9092）
```
$ bin/kafka-server-start.sh config/server.properties > logs/kafka-server.log 2>&1 &
```

4.创建一个主题（Topic）
```
# 创建一个名为“test”的Topic，只有一个分区和一个备份：
$ bin/kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic test

# 查看topic列表：
$ bin/kafka-topics.sh --list --zookeeper localhost:2182

# 查看topic的状态：
$ bin/kafka-topics.sh --describe --zookeeper localhost:2182 --topic test
```

5.发送/消费消息

```
# 发送消息
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

# 消费消息
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
```


### Kafka Streams
1.Kafka Streams是一个客户端程序库，用于处理和分析存储在Kafka中的数据，并将得到的数据写回Kafka或发送到外部系统。Kafka Stream
基于一个重要的流处理概念。如正确的区分事件时间和处理时间，窗口支持，以及简单而有效的应用程序状态管理。

2.Kafka Stream 的亮点：

- 1）设计一个简单的、轻量级的客户端库，可以很容易地嵌入在任何java应用程序与任何现有应用程序封装集成。
- 2）Apache Kafka本身作为内部消息层，没有外部系统的依赖，还有，它使用kafka的分区模型水平扩展处理，并同时保证有序。
- 3）支持本地状态容错，非常快速、高效的状态操作（如join和窗口的聚合）。
- 4）采用 one-recored-at-a-time（一次一个消息） 处理以实现低延迟，并支持基于事件时间(event-time)的窗口操作。
- 5）提供必要的流处理原语(primitive)，以及一个 高级别的Steram DSL 和 低级别的Processor API。


### Maven依赖
```
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>0.10.0.0</version>
</dependency>
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-streams</artifactId>
    <version>0.10.0.0</version>
</dependency>
```

### Kafka的四个核心API

- 应用程序使用`Producer API`发布消息到1个或多个topic（主题）。

- 应用程序使用`Consumer API`来订阅一个或多个topic，并处理产生的消息。

- 应用程序使用`Streams API`充当一个流处理器，从1个或多个topic消费输入流，并生产一个输出流到1个或多个输出topic，有效地将输入流转换到输出流。

- `Connector API`允许构建或运行可重复使用的生产者或消费者，将topic连接到现有的应用程序或数据系统。例如，一个关系数据库的连接器可捕获每一个变化。

![core-api](./doc/core-api.png)

Client和Server之间的通讯，是通过一条简单、高性能并且和开发语言无关的TCP协议。并且该协议保持与老版本的兼容。Kafka除了提供Java Client外，
还有非常多的其它编程语言的Client。


### 主题和日志 (Topic和Log)

Topic是发布的消息的类别或者种子Feed名。对于每一个Topic，Kafka集群维护这一个分区的log，就像下图中的示例：

![anatomy-topic](./doc/anatomy-topic.png)

每一个分区都是一个顺序的、不可变的消息队列， 并且可以持续的添加。分区中的消息都被分了一个序列号，称之为偏移量(offset)，在每个分区
中此偏移量都是唯一的。

Kafka集群保持所有的消息，直到它们过期， 无论消息是否被消费了。 实际上消费者所持有的仅有的元数据就是这个偏移量，也就是消费者在这个
log中的位置。这个偏移量由消费者控制：正常情况当消费者消费消息的时候，偏移量也线性的的增加。但是实际偏移量由消费者控制，消费者可以
将偏移量重置为更老的一个偏移量，重新读取消息。可以看到这种设计对消费者来说操作自如，一个消费者的操作不会影响其它消费者对此log的处理。


### 分布式(Distribution)

Log的`分区`被分布到集群中的多个服务器上。每个服务器处理它分到的分区。 根据配置每个`分区`还可以复制到其它服务器作为`备份`容错。 
每个分区有一个leader，零或多个follower。Leader处理此分区的所有的读写请求，而follower被动的复制数据。如果leader宕机，其它的
一个follower会被推举为新的leader。 一台服务器可能同时是一个分区的leader，另一个分区的follower。 这样可以平衡负载，避免所有
的请求都只让一台或者某几台服务器处理。


### 生产者(Producers)

生产者往某个Topic上发布消息。生产者也负责选择发布到Topic上的哪一个分区。最简单的方式从分区列表中轮流选择。也可以根据某种算法依照
权重选择分区。`开发者负责如何选择分区的算法`。


### 消费者(Consumers)

通常来讲，消息模型可以分为两种， 队列和发布-订阅式。 队列的处理方式是 一组消费者从服务器读取消息，一条消息只有其中的一个消费者来
处理。在发布-订阅模型中，消息被广播给所有的消费者，接收到消息的消费者都可以处理此消息。

Kafka为这两种模型提供了单一的消费者抽象模型：`消费者组（consumer group）`，`消费者`用一个`消费者组名`标记自己。一个发布在Topic
上消息被分发给此`消费者组`中的一个`消费者`。 假如所有的`消费者`都在一个组中，那么这就变成了`queue模型`。 假如所有的消费者都在不
同的组中，那么就完全变成了`发布-订阅模型`。 更通用的，我们可以创建一些`消费者组`作为逻辑上的订阅者。每个组包含数目不等的`消费者`，
一个组内多个`消费者`可以用来扩展性能和容错。正如下图所示：

![consumer-map](./doc/consumer-map.png)

2个kafka集群托管4个分区（P0-P3），2个消费者组，消费组A有2个消费者实例，消费组B有4个。


### Kafka的保证(Guarantees)

- 生产者发送到一个特定的Topic的分区上，消息将会按照它们发送的顺序依次加入，也就是说，如果一个消息M1和M2使用相同的producer发送，
M1先发送，那么M1将比M2的offset低，并且优先的出现在日志中。

- 消费者收到的消息也是此顺序。

- 如果一个Topic配置了复制因子（replication factor）为N， 那么可以允许N-1服务器宕机而不丢失任何已经提交（committed）的消息。


### kafka作为一个消息系统

> Kafka的流与传统企业消息系统相比的概念如何？

传统的消息有两种模式：队列和发布订阅。 在队列模式中，消费者池从服务器读取消息（每个消息只被其中一个读取）; 发布订阅模式：消息广播
给所有的消费者。这两种模式都有优缺点，队列的优点是允许多个消费者瓜分处理数据，这样可以扩展处理。但是，队列不像多个订阅者，一旦消息
者进程读取后故障了，那么消息就丢了。而发布和订阅允许你广播数据到多个消费者，由于每个订阅者都订阅了消息，所以没办法缩放处理。

kafka中消费者组有两个概念：

- 队列：消费者组（consumer group）允许同名的消费者组成员瓜分处理。

- 发布订阅：允许你广播消息给多个消费者组（不同名）。

> kafka有比传统的消息系统更强的顺序保证。

传统的消息系统按顺序保存数据，如果多个消费者从队列消费，则服务器按存储的顺序发送消息，但是，尽管服务器按顺序发送，消息异步传递到
消费者，因此消息可能乱序到达消费者。这意味着消息存在并行消费的情况，顺序就无法保证。消息系统常常通过仅设1个消费者来解决这个问题，
但是这意味着没用到并行处理。

kafka做的更好。通过并行topic的parition —— kafka提供了顺序保证和负载均衡。每个partition仅由同一个消费者组中的一个消费者消费到。
并确保消费者是该partition的唯一消费者，并按顺序消费数据。每个topic有多个分区，则需要对多个消费者做负载均衡，但请注意，`相同的消
费者组中不能有比分区更多的消费者，否则多出的消费者一直处于空等待，不会收到消息`。


### kafka作为一个存储系统

所有发布消息到`消息队列`和消费分离的系统，实际上都充当了一个存储系统（发布的消息先存储起来）。Kafka比别的系统的优势是它是一个非常
高性能的`存储系统`。

写入到kafka的数据将写到磁盘并复制到集群中保证容错性。并允许生产者等待消息应答，直到消息完全写入。

kafka的磁盘结构 - 无论你服务器上有50KB或50TB，执行是相同的。

client来控制读取数据的位置。你还可以认为kafka是一种专用于高性能，低延迟，提交日志存储，复制，和传播特殊用途的`分布式文件系统`。


### kafka的流处理

仅仅读，写和存储是不够的，kafka的目标是实时的流处理。

在kafka中，流处理持续获取输入topic的数据，进行处理加工，然后写入输出topic。例如，一个零售APP，接收销售和出货的输入流，统计数量
或调整价格后输出。

可以直接使用producer和consumer API进行简单的处理。对于复杂的转换，Kafka提供了更强大的Streams API。可构建`聚合计算`或`连接流
到一起`的复杂应用程序。

助于解决此类应用面临的硬性问题：处理无序的数据，代码更改的再处理，执行状态计算等。

Sterams API在Kafka中的核心：使用producer和consumer API作为输入，利用Kafka做状态存储，使用相同的组机制在stream处理器实例之
间进行容错保障。


### kafka面试题

- kafka节点之间如何复制备份的？
- kafka消息是否会丢失？为什么？
- kafka最合理的配置是什么？
- kafka的leader选举机制是什么？
- kafka对硬件的配置有什么要求？
- kafka的消息保证有几种方式？
- kafka为什么会丢消息？