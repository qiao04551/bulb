package com.maxzuo.protect;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * Sentinel Metric信息Kafka消费者
 * <p>
 * Created by zfh on 2019/07/12
 */
public class SentinelMetricConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SentinelMetricConsumer.class);

    private static final String BOOTSTRAP_SERVERS = "139.129.216.37:9092";

    private static final String METRIC_TOPIC = "sentinel-metric";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", "consumer-one");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(METRIC_TOPIC), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            }
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            }
        });

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("key: " + record.key() + " value = " + record.value());
            }
        }
    }
}
