package com.maxzuo.mqtt;

import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * mqtt客户端连接mqtt服务器-消息发布者
 * <p>
 * Created by zfh on 2019/06/16
 */
public class MqttPublish {

    private static final Logger logger = LoggerFactory.getLogger(MqttPublish.class);

    private static final String HOST_ADDRESS = "tcp://mqtt.xxx.com:1883";

    private static final String TOPIC = "testtopic";

    private static final AtomicInteger MSG_COUNT = new AtomicInteger(0);

    public static void main (String[] args) {
        try {
            MQTT mqtt = new MQTT();
            mqtt.setHost(HOST_ADDRESS);
            mqtt.setClientId("java-client-publish");
            mqtt.setUserName("publish");
            mqtt.setPassword("publish");

            CallbackConnection connection = mqtt.callbackConnection();
            connection.connect(new Callback<Void>() {
                @Override
                public void onSuccess(Void value) {
                    logger.info("连接成功！");
                }

                @Override
                public void onFailure(Throwable value) {
                    logger.error("连接失败，errMessage: {}", value.getMessage(), value);
                }
            });

            for (int i = 0; i < 10; i++) {
                connection.publish(TOPIC, "hello EMQ".getBytes(), QoS.AT_MOST_ONCE, false, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void value) {
                        logger.info(MSG_COUNT.incrementAndGet() + " 发布消息成功！");
                    }

                    @Override
                    public void onFailure(Throwable value) {
                        logger.error("发布消息失败，errMessage：{}", value.getMessage(), value);
                    }
                });
            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
