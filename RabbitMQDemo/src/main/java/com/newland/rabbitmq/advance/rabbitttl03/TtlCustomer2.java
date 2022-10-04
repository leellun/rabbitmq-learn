package com.newland.rabbitmq.advance.rabbitttl03;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 为消息设置 TTL
 */
public class TtlCustomer2 {
    /** 队列名称 */
    private final String QUEUE_NAME = "queue-ttl2";

    public static void main(String[] args) throws Exception {
        TtlCustomer2 demo = new TtlCustomer2();
        demo.receiver();
    }

    /**
     * 生产者
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
