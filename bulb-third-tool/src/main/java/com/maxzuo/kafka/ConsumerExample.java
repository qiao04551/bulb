package com.maxzuo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Kafka 消费者（0.10.0.0）
 * <pre>
 *   随着0.9.0版本，已经增加了一个新的Java消费者替换现有的基于zookeeper的高级和低级消费者。
 * </pre>
 * Created by zfh on 2018/10/14
 */
public class ConsumerExample {

    /**
     * test：47.98.199.80:9092
     * cluster：192.168.3.192:9090,192.168.3.191:9090,192.168.3.181:9090
     */
    private static final String BOOTSTRAP_SERVERS = "47.98.199.80:9092";

    /**
     * 1.偏移量和消费者的位置
     *   1）kafka为分区中的每条消息保存一个偏移量（offset），这个偏移量是该分区中一条消息的唯一标示符。也表示消费者在分区的位置。
     *   2）消费者的位置给出了下一条记录的偏移量。它比消费者在该分区中看到的最大偏移量要大一个。 它在每次消费者在调用poll(long)
     *     中接收消息时自动增长。
     *   3）“已提交”的位置是已安全保存的最后偏移量，如果进程失败或重新启动时，消费者将恢复到这个偏移量。消费者可以选择定期自动提交
     *     偏移量，也可以选择通过调用commit API来手动的控制(如：commitSync 和 commitAsync)。
     *   4）Kafka消费者在早先的版本中offset默认存储在ZooKeeper，当前版本 消费者的偏移量 存储在Kafka中。
     *
     * 2.消费者组和主题订阅
     *   1）Kafka的消费者组概念，通过进程池瓜分消息并处理消息。这些进程可以在同一台机器运行，也可分布到多台机器上，以增加可扩展性
     *     和容错性，相同group.id的消费者将视为同一个消费者组。
     *   2）分组中的每个消费者都通过subscribe API动态的订阅一个topic列表。kafka将已订阅topic的消息发送到每个消费者组中。并通过
     *     平衡分区在消费者分组中所有成员之间来达到平均。因此每个分区恰好地分配1个消费者（一个消费者组中）。所有如果一个topic有4
     *     个分区，并且一个消费者分组有只有2个消费者。那么每个消费者将消费2个分区。
     *   3）消费者组的成员是动态维护的：如果一个消费者故障。分配给它的分区将重新分配给同一个分组中其他的消费者。同样的，如果一个新
     *     的消费者加入到分组，将从现有消费者中移一个给它。这被称为重新平衡分组，并在下面更详细地讨论。当新分区添加到订阅的topic时，
     *     或者当创建与订阅的正则表达式匹配的新topic时，也将重新平衡。将通过定时刷新自动发现新的分区，并将其分配给分组的成员。
     *   4）当分组重新分配自动发生时，可以通过ConsumerRebalanceListener通知消费者，这允许他们完成必要的应用程序级逻辑，例如状态
     *     清除，手动偏移提交等。
     *   5）它也允许消费者通过使用assign(Collection)手动分配指定分区，如果使用手动指定分配分区，那么动态分区分配和协调消费者组将
     *     失效。
     *
     * 3.发现消费者故障
     *    订阅一组topic后，当调用poll(long）时，消费者将自动加入到组中。只要持续的调用poll，消费者将一直保持可用，并继续从分配
     *    的分区中接收消息。此外，消费者向服务器定时发送心跳。 如果消费者崩溃或无法在session.timeout.ms配置的时间内发送心跳，则
     *    消费者将被视为死亡，并且其分区将被重新分配。
     *
     * 4.多线程处理
     *    1）Kafka消费者不是线程安全的。所有网络I/O都发生在进行调用应用程序的线程中。用户的责任是确保多线程访问正确同步的。非同步
     *      访问将导致ConcurrentModificationException。此规则唯一的例外是wakeup()，它可以安全地从外部线程来中断活动操作。
     *    2）Kafka没有多线程模型的例子，但留下几个操作可用来实现多线程处理消息：
     *      a.每个线程一个消费者
     *      b.解耦消费和处理
     *        另一个替代方式是一个或多个消费者线程，它来消费所有数据，其消费所有数据并将ConsumerRecords实例切换到由实际处理记录
     *        处理的处理器线程池来消费的阻塞队列。
     *
     * 5.Kafka文档：https://www.orchome.com/451
     */
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
        // 订阅Topic
        consumer.subscribe(Arrays.asList("test", "test2"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("key: " + record.key() + " value = " + record.value());
            }

            /// 手动提交
            // consumer.commitSync();
            try {
                TimeUnit.MICROSECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
