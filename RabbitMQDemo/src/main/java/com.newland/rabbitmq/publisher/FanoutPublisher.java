package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式-fanout
 * 1） 可以有多个消费者
 * 2） 每个消费者有自己的queue（队列）
 * 3） 每个队列都要绑定到Exchange（交换机）
 * 4） 生产者发送的消息，只能发送到交换机，交换机来决定要发给哪个队列，生产者无法决定。
 * 5） 交换机把消息发送给绑定过的所有队列
 * 6） 队列的消费者都能拿到消息。实现一条消息被多个消费者消费
 *                ——》消息队列——》消费者
 *  生产者——》交换机
 *                ——》消息队列——》消费者
 */
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
