package com.maxzuo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Kafka-生产者
 * Created by zfh on 2018/10/14
 */
public class ProducerExample {

    /**
     * cluster：192.168.3.192:9090,192.168.3.191:9090,192.168.3.181:9090
     */
    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";

    /**
     * 消息是Kafka通信的基本单位，有一个固定长度的消息头和一个可变长度的消息体构成。在老版本中，每一条消息称为Message；
     * 在Java重新实现的客户端中，每一条消息称为Record，由一个key，一个value和时间戳构成。
     */
    public static void main (String[] args) {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        prop.put("acks", "all");
        prop.put("retries", 0);
        prop.put("linger.ms", 1);
        prop.put("buffer.memory", 33554432);
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        for (int i = 0; i < 1; i++) {
            // 通过key去指定分区和使用hash来指向分区（如果需要，可重写分区函数）
            ProducerRecord<String, String> record = new ProducerRecord<>("quickstart-events", "user","hello kafka" + "  i = " + i);
            record.headers().add("name", "dazuo".getBytes()).add("age", "2343".getBytes());

            // send()方法是异步的，添加消息到缓冲区等待发送，并立即返回。
            Future<RecordMetadata> future = producer.send(record);
            try {
                // 指定了消息发送的分区，分配的offset和消息的时间戳
                RecordMetadata recordMetadata = future.get();
                int partition = recordMetadata.partition();
                System.out.println("partition: " + partition);
                System.out.println("offset: " + recordMetadata.offset());
                System.out.println("topic: " + recordMetadata.topic());
                System.out.println("timestamp: " + recordMetadata.timestamp());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.close();
        System.out.println("Producer end ...");
    }
}
