package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式-topic
 * Topic类型的Exchange与Direct相比，都是可以根据RoutingKey把消息路由到不同的队列。
 * 只不过Topic类型Exchange可以让队列在绑定Routing key 的时候使用通配符！
 */
public class TopicPublisher {
    public static final String EXCHANGE="topic_exchange";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE,"topic",true);
        channel.basicPublish(EXCHANGE,"item.delete.product",null,"topic类型".getBytes());
        channel.close();
        connection.close();
    }
}
