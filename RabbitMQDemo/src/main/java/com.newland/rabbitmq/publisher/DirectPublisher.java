package com.newland.rabbitmq.publisher;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式-direct
 * 队列与交换机的绑定，不能是任意绑定了，而是要指定一个RoutingKey（路由key）
 * 消息的发送方在 向 Exchange发送消息时，也必须指定消息的 RoutingKey。
 * Exchange不再把消息交给每一个绑定的队列，而是根据消息的Routing Key进行判断，只有队列的Routingkey与消息的 Routing key完全一致，才会接收到消息
 */
public class DirectPublisher {
    public static final String EXCHANGE="direct_exchange";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish(EXCHANGE,"insert",null,"这是一条direct insert消息".getBytes());
        channel.basicPublish(EXCHANGE,"update",null,"这是一条direct update消息".getBytes());
        channel.basicPublish(EXCHANGE,"delete",null,"这是一条direct delete消息".getBytes());
        channel.close();
        connection.close();
    }
}
