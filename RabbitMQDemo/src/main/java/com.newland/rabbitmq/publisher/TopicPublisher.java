package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TopicPublisher {
    public static final String EXCHANGE="topic_exchange";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE,"topic",true);
        channel.basicPublish(EXCHANGE,"item.delete.product",null,"topic类型".getBytes());
        channel.close();
        connection.close();
    }
}
