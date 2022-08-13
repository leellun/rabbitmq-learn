package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据消息的headers来匹配对应的队列，在消息接收回调中指定headers， 可以是Map<String, Object>、String可变数组类型的keys等
 *
 */
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
