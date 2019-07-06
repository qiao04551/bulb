package com.maxzuo.protect;

import com.alibaba.csp.sentinel.Constants;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.node.metric.MetricNode;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Sentinel Metric信息收集
 * <p>
 * Created by zfh on 2019/02/15
 */
@Component
@EnableScheduling
public class SentinelMetricCollect {

    private static final Logger logger = LoggerFactory.getLogger(SentinelMetricCollect.class);

    @Value("${bootstrap.servers}")
    private String bootstrapServers;

    @Value("${metric.topic}")
    private String metricTopic;

    private KafkaProducer kafkaProducer;

    @PostConstruct
    public void init () {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", bootstrapServers);
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        try {
            kafkaProducer = new KafkaProducer(prop);
        } catch (Exception e) {
            logger.error("【Sentinel Metric信息收集】初始化异常！", e);
        }
    }

    /**
     * 用Kafka替换默认的日志文件 {@link com.alibaba.csp.sentinel.node.metric.MetricTimerListener}
     */
    @Scheduled(cron = "0/1 * * * * ?")
    private void run () {
        Map<Long, List<MetricNode>> maps = new TreeMap<Long, List<MetricNode>>();
        for (Map.Entry<ResourceWrapper, ClusterNode> e : ClusterBuilderSlot.getClusterNodeMap().entrySet()) {
            String name = e.getKey().getName();
            ClusterNode node = e.getValue();
            Map<Long, MetricNode> metrics = node.metrics();
            aggregate(maps, metrics, name);
        }
        // 系统负载的度量
        aggregate(maps, Constants.ENTRY_NODE.metrics(), Constants.TOTAL_IN_RESOURCE_NAME);
        if (!maps.isEmpty()) {
            for (Map.Entry<Long, List<MetricNode>> entry : maps.entrySet()) {
                try {
                    // TODO: 采集
                    ProducerRecord<String, String> record = new ProducerRecord<>("test", "user",entry.getKey().toString());
                    kafkaProducer.send(record);

                    List<MetricNode> value = entry.getValue();

                    logger.info("K => {}  V => {}",  entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    logger.error("【Sentinel Metric信息收集】发生异常！", e);
                }
            }
        }
    }

    private void aggregate(Map<Long, List<MetricNode>> maps, Map<Long, MetricNode> metrics, String resourceName) {
        for (Map.Entry<Long, MetricNode> entry : metrics.entrySet()) {
            long time = entry.getKey();
            MetricNode metricNode = entry.getValue();
            metricNode.setResource(resourceName);
            maps.computeIfAbsent(time, k -> new ArrayList<>());
            List<MetricNode> nodes = maps.get(time);
            nodes.add(entry.getValue());
        }
    }
}
