package com.newland.rabbitmq.advance.rabbitmqmirror08;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 镜像队列发布
 */
public class DirectPublisher {
    public static final String EXCHANGE="mirror_exchange";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.newConnection("192.168.66.70",5674);
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE,"direct",true,true,null);
        channel.basicPublish(EXCHANGE,"insert",null,"这是一条direct insert消息".getBytes());
        channel.close();
        connection.close();
    }
}
