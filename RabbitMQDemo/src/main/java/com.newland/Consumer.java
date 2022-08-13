package com.newland;

import com.rabbitmq.client.*;

/**
 * @Author Mqs
 * @Date 2018/10/27 22:16
 * @Desc
 */
public class Consumer {
    public static void main(String[] args)throws Exception {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("liulun666");
        factory.setVirtualHost("/");
        factory.setHost("192.168.10.103");
        // 2、创建连接
        Connection connection = factory.newConnection();
        // 3、获取通道
        Channel channel = connection.createChannel();

        String exchangeName = "exchange2";
        String routingKey = "return.key";
        String exchangeType = "direct";
        String queueName = "return_queue";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);

        channel.queueDeclare(queueName, true, false, false, null);

        channel.queueBind(queueName, exchangeName, routingKey);


        channel.basicConsume(queueName, true, new ConsumerCallback(channel));
    }
}
