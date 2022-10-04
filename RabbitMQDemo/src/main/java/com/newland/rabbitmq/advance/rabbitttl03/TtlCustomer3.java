package com.newland.rabbitmq.advance.rabbitttl03;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 为队列设置 TTL，过期后队列将自动删除
 */
public class TtlCustomer3 {
    /**
     * 队列名称
     */
    private final String QUEUE_NAME = "queue-ttl3";

    public static void main(String[] args) throws Exception {
        TtlCustomer3 demo = new TtlCustomer3();
        demo.receiver();
    }

    /**
     * 生产者
     *
     * @throws Exception
     */
    private void receiver() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("ttl:" + new String(body));
            }
        });
    }

}
