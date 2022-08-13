package com.newland.rabbitmq.consumer;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class SimpleConsumer {
    public static final String QUEUE_NAME = "sdfsdfsdfssf1";

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("message receive:" + new String(body));
            }
        });
    }
}
