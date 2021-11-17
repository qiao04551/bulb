package com.maxzuo.kafka;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Kafka Streams流处理程序（0.10.0.0）Kafka Streams从一个或多个输入topic进行连续的计算并输出到0或多个外部topic中。
 *
 * <pre>
 *  仅仅读，写和存储是不够的，kafka的目标是实时的流处理。
 *
 *  在kafka中，流处理持续获取输入topic的数据，进行处理加工，然后写入输出topic。例如，一个零售APP，接收销售和出货的输入流，统计数量
 *  或调整价格后输出。
 *
 *  可以直接使用 producer 和 consumer API 进行简单的处理。对于复杂的转换，Kafka提供了更强大的 Streams API。可构建`聚合计算`或
 *  `连接流到一起`的复杂应用程序。
 *
 *  助于解决此类应用面临的硬性问题：处理无序的数据，代码更改的再处理，执行状态计算等。
 *
 *  Sterams API在Kafka中的核心：使用producer和consumer API作为输入，利用Kafka做状态存储，使用相同的组机制在stream处理器实例之
 *  间进行容错保障。
 * </pre>
 * Created by zfh on 2019/03/06
 */
public class StreamsExample {

    private static final Logger logger            = LoggerFactory.getLogger(StreamsExample.class);

    /**
     * cluster：192.168.3.192:9090,192.168.3.191:9090,192.168.3.181:9090
     */
    private static final String BOOTSTRAP_SERVERS = "192.168.3.192:9092";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<Object, Object> source = builder.stream("quickstart-events").mapValues(value -> {
            logger.info("value: {}", new String((byte[]) value));
            return value;
        });
        /// 投递到新的topic上
        //source.to("output-topic");

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        final CountDownLatch latch = new CountDownLatch(1);

        // 捕获用户中断，并在终止该程序时关闭客户端
        Runtime.getRuntime().addShutdownHook(new Thread("kafka-stream-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
        try {
            // start()函数，触发客户端的执行。在此客户端上调用close()之前，执行不会停止。
            streams.start();
            latch.await();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
