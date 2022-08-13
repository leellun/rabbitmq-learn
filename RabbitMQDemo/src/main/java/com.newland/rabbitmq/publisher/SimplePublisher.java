package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 基本模型：
 * 生产者——消息队列 ——消费者
 */
public class SimplePublisher {
    public static final String QUEUE_NAME = "sdfsdfsdfssf1";

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, String.format("第%d条信息", i).getBytes());
            Thread.sleep(200);
        }
        channel.close();
        connection.close();
    }
}
