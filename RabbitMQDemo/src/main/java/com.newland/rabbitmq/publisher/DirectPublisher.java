package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class DirectPublisher {
    public static final String EXCHANGE="direct_exchange";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish(EXCHANGE,"insert",null,"这是一条direct insert消息".getBytes());
        channel.basicPublish(EXCHANGE,"update",null,"这是一条direct update消息".getBytes());
        channel.basicPublish(EXCHANGE,"delete",null,"这是一条direct delete消息".getBytes());
        channel.close();
        connection.close();
    }
}
