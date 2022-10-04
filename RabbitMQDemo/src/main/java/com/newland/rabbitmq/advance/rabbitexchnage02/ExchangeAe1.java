package com.newland.rabbitmq.advance.rabbitexchnage02;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用 channel.exchangeDeclare 声明一个备份交换器
 */
public class ExchangeAe1 {

    public static void main(String[] args) throws Exception {
        ExchangeAe1 demo = new ExchangeAe1();
        demo.consumer();
        demo.consumer2();
        demo.sender();
    }

    /**
     * 生产者
     * @throws Exception
     */
    private void sender() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        // 声明交换器
        // 下面交换器用作备份交换器，该交换器的所有消息均路由到 unroutedQueue 队列
        channel.exchangeDeclare("myAe","fanout",
                true , false, null) ;
        channel.queueDeclare("unroutedQueue", true,
                false , false, null);
        channel.queueBind("unroutedQueue","myAe","");

        // 下面交换器通过 alternate-exchange 参数设置一个备份交换器
        // 将交换器 myAe 设置为 normalExchange 的交换器
        Map<String, Object> exchangeArgs = new HashMap<String, Object>();
        exchangeArgs.put("alternate-exchange", "myAe");
        channel.exchangeDeclare("normalExchange","direct",
                true, false, exchangeArgs);
        channel.queueDeclare("normalQueue", true,
                false , false , null);
        channel.queueBind("normalQueue", "normalExchange", "normalKey");

        // 发送消息
        System.out.println("[Sender] 开始发送消息...");
        // 该消息将路由给 normalQueue 队列
        channel.basicPublish("normalExchange", "normalKey",
                null, "hello message1".getBytes());
        System.out.println("[Sender] 发送消息 “hello message1”");

        // 该消息不能匹配任何队列，将发往备份交换器
        channel.basicPublish("normalExchange", "www.hxstrive.com",
                null, "hello message2".getBytes());
        System.out.println("[Sender] 发送消息 “hello message2”");

        // 关闭连接
        channel.close();
        connection.close();
    }

    /**
     * 消费者
     * @throws Exception
     */
    private void consumer() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("normalQueue", true,
                false , false , null);

        // 消费消息
        System.out.println("[Consumer] 开始消费消息...");
        channel.basicConsume("normalQueue", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[Consumer] body = " + new String(body));
            }
        });
    }

    /**
     * 消费者
     * @throws Exception
     */
    private void consumer2() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("unroutedQueue", true,
                false , false , null);
        // 消费消息
        System.out.println("[Consumer2] 开始消费消息...");
        channel.basicConsume("unroutedQueue", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[Consumer2] body = " + new String(body));
            }
        });
    }

}