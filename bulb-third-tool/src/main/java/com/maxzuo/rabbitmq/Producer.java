package com.maxzuo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 * Created by zfh on 2018/10/28
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";
        // 发送数据，只需要往指定的交换机上投递，指定routingKey
        channel.basicPublish(exchangeName, routingKey, null, "hello RabbitMQ".getBytes());
        // 关闭连接
        channel.close();
        connection.close();
    }
}
