package com.maxzuo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息消费方
 * Created by zfh on 2018/10/27
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1.创建一个ConnectionFactory，并进行配置
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";
        String routingKey = "test.direct";

        // 声明交换机和队列，建立绑定
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 创建消费者消费消息
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 设置Channel
        channel.basicConsume(queueName, true, queueingConsumer);
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("Listener msg: " + new String(delivery.getBody()));
        }
    }
}
