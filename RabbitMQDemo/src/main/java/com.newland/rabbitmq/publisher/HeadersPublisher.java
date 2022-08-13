package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

public class HeadersPublisher {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        Map<String,Object> headers=new HashMap<String, Object>();
        headers.put("test","testheaders1");
        AMQP.BasicProperties props=new AMQP.BasicProperties.Builder().headers(headers).build();
        channel.basicPublish("","headersqueue",props,"headers类型".getBytes());
        channel.close();
        connection.close();
    }
}
