package com.newland.rabbitmq.advance.rabbitmqdelay07;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 延迟插件实现延迟任务
 * Author: leell
 * Date: 2022/10/5 01:40:58
 */
public class DelayDemo {
    /**
     * 普通交换器名称
     */
    private final String exchangeName = "exchange_delay";
    /**
     * 队列名称
     */
    private final String queueName = "queue_delay";

    public static void main(String[] args) throws Exception {
        DelayDemo demo = new DelayDemo();
        demo.sender();
        demo.consumer();
    }

    /**
     * 生产者
     *
     * @throws Exception
     */
    private void sender() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 创建信道
        Channel channel = connection.createChannel();

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-delayed-type", "direct");
        // 声明普通交换器和队列
        channel.exchangeDeclare(exchangeName, "x-delayed-message", false, false, arguments);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, "");

        // 发送带有TTL过期时间的消息
        System.out.println("[Sender] Send Message...");
        String message = "exchange delay message";
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-delay", 5000);
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .headers(headers)
                .build();
        channel.basicPublish(exchangeName, "", properties, message.getBytes());

        System.out.println("[Sender] message = '" + message + "'");

        // 关闭连接
        channel.close();
        connection.close();
    }

    /**
     * 消费消息，从死信队列消费消息
     *
     * @throws Exception
     */
    private void consumer() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 创建信道
        final Channel channel = connection.createChannel();

        // 消费消息
        System.out.println("[Consumer] Waiting Message...");
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[Consumer] body = " + new String(body));
            }
        });
    }
}
