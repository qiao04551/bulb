package com.maxzuo.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * 使用Eclipse Paho客户端连接
 * <p>
 * Created by zfh on 2019/07/26
 */
public class EclipsePahoClient {

    private static final Logger logger = LoggerFactory.getLogger(EclipsePahoClient.class);

    private static final String TOPIC = "testtopic";

    private static final String CLIENT_Id = "eclipse-paho-client-" + LocalDateTime.now().getNano();

    private static final String BROKER = "tcp://118.190.141.231:1883";

    public static void main(String[] args) {
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient(BROKER, CLIENT_Id, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(false);
            connectOptions.setKeepAliveInterval(5);
            connectOptions.setAutomaticReconnect(true);
            mqttClient.connect(connectOptions);

            // 订阅频道
            mqttClient.subscribe(TOPIC,2, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("收到来自 " + topic + " 的消息：" + message.toString());
                }
            });

            // 发布消息
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(("hello emq！" + LocalDateTime.now().toString()).getBytes());
            mqttMessage.setQos(2);
            mqttClient.publish(TOPIC, mqttMessage);

            // 继续订阅
            mqttClient.subscribe(TOPIC + "2", 2, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("收到来自 " + topic + " 的消息：" + message.toString());
                }
            });

            Thread.sleep(5000);
            // 取消订阅
            mqttClient.unsubscribe(TOPIC);

            // TODO: 关闭客户端和断开连接
            mqttClient.close();
            // mqttClient.disconnect();
        } catch (Exception e) {
            logger.error("全局发生异常！", e);
        }
    }
}
