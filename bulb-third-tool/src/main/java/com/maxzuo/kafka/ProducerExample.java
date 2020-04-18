package com.maxzuo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 生产者
 * Created by zfh on 2018/10/14
 */
public class ProducerExample {

    /** test：47.98.199.80:9092 cluster：192.168.3.192:9090,192.168.3.191:9090,192.168.3.181:9090 */
    private static final String BOOTSTRAP_SERVERS = "47.98.199.80:9092";

    public static void main (String[] args) {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(prop);
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("test", "user","hello kafka" + "  i = " + i);
            kafkaProducer.send(record);
        }
        kafkaProducer.close();
        System.out.println("Producer end ...");
    }
}
