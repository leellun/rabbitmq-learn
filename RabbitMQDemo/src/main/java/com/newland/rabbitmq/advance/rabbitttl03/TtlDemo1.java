package com.newland.rabbitmq.advance.rabbitttl03;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 为消息设置 TTL
 */
public class TtlDemo1 {
    /** 交换器名称 */
    private final String EXCHANGE_NAME = "exchange-ttl1";
    /** 队列名称 */
    private final String QUEUE_NAME = "queue-ttl1";

    public static void main(String[] args) throws Exception {
        TtlDemo1 demo = new TtlDemo1();
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
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, true, null) ;
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 发送消息
        System.out.println("[Sender] Send Message...");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .expiration("6000") // 设置 TTL=6秒
                .build();

        channel.basicPublish(EXCHANGE_NAME, "",
                properties, "ttl message".getBytes());
        System.out.println("[Sender] message = “ttl message”");

        // 关闭连接
        channel.close();
        connection.close();
    }

}
