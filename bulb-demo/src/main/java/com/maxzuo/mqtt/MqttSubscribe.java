package com.maxzuo.mqtt;

import org.fusesource.mqtt.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * mqtt客户端连接mqtt服务器-消息订阅者
 * <p>
 * Created by zfh on 2019/06/16
 */
public class MqttSubscribe {

    private static final Logger logger = LoggerFactory.getLogger(MqttSubscribe.class);

    private static final String HOST_ADDRESS = "tcp://mqtt.xxx.com:1883";

    private static final String TOPIC = "testtopic";

    private static final AtomicInteger MSG_COUNT = new AtomicInteger(0);

    /*
        TODO:
          1.订阅哪个频道（每个桌号一个频道），动态订阅。
            服务端：监听开台消息，增加桌台订阅；监听关台，取消桌台订阅。
     */
    public static void main(String[] args) {
        try {
            MQTT mqtt = new MQTT();
            mqtt.setHost(HOST_ADDRESS);
            mqtt.setClientId("java-client-subscribe");
            mqtt.setUserName("subscribe");
            mqtt.setPassword("subscribe");

            FutureConnection connection = mqtt.futureConnection();
            Future<Void> connect = connection.connect();
            connect.await();
            logger.info("连接成功！");

            Topic[] topics = {new Topic(TOPIC, QoS.AT_MOST_ONCE)};
            Future<byte[]> subscribe = connection.subscribe(topics);
            subscribe.await();
            logger.info("订阅成功！");

            while (connection.isConnected()) {
                Future<Message> receive = connection.receive();
                Message message = receive.await();
                logger.info(MSG_COUNT.incrementAndGet() + " 收到来自{}的消息：{}", message.getTopic(), new String(message.getPayload()));
                Thread.sleep(1000);

                if (MSG_COUNT.get() == 10) {
                    Topic[] dynamicTopics = {new Topic("dazuo", QoS.AT_MOST_ONCE)};
                    connection.subscribe(dynamicTopics);
                    logger.info("动态订阅：{}", Arrays.toString(dynamicTopics));
                }
                if (MSG_COUNT.get() == 11) {
                    String[] dynamicTopics = {"dazuo"};
                    connection.unsubscribe(dynamicTopics);
                    logger.info("动态取消订阅：{}", Arrays.toString(dynamicTopics));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
