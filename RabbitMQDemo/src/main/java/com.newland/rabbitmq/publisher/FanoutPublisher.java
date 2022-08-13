package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class FanoutPublisher {
    public static final String EXCHANGE="fanout_exchange";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish(EXCHANGE,"",null,"这是一条fanout消息".getBytes());
        channel.close();
        connection.close();
    }
}
