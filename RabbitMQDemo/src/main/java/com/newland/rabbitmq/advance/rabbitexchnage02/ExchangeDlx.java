package com.newland.rabbitmq.advance.rabbitexchnage02;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用 channel.exchangeDeclare 声明一个备份交换器
 */
public class ExchangeDlx {
    /** 死信交换器名称 */
    private final String exchangeDlxName = "exchange_order_dlx";
    /** 普通交换器名称 */
    private final String exchangeName = "exchange_order";
    /** 死信队列名称 */
    private final String queueDlxName = "queue_order_dlx";
    /** 队列名称 */
    private final String queueName = "queue_order" ;

    public static void main(String[] args) throws Exception {
        ExchangeDlx demo = new ExchangeDlx();
        demo.consumer();
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

        // 声明死信交换器和死信队列
        channel.exchangeDeclare(exchangeDlxName, "direct");
        channel.queueDeclare(queueDlxName, true, false, true, null);
        channel.queueBind(queueDlxName, exchangeDlxName, "");

        // 声明普通交换器和队列
        channel.exchangeDeclare(exchangeName, "direct");
        Map<String,Object> queueArgs = new HashMap<String, Object>();
        // 为队列设置死信交换器
        queueArgs.put("x-dead-letter-exchange", exchangeDlxName);
        channel.queueDeclare(queueName, true, false, false, queueArgs);
        channel.queueBind(queueName, exchangeName, "");

        // 发送带有TTL过期时间的消息
        System.out.println("[Sender] Send Message...");
        String message = "exchange DLX message";
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .expiration("10000") // 设置 TTL=10秒
                .build();
        channel.basicPublish(exchangeName, "", properties, message.getBytes());
        System.out.println("[Sender] message = '" + message + "'");

        // 关闭连接
        channel.close();
        connection.close();
    }

    /**
     * 消费消息，从死信队列消费消息
     * @throws Exception
     */
    private void consumer() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 创建信道
        final Channel channel = connection.createChannel();

        // 声明队列
        // 声明死信交换器和死信队列
        channel.exchangeDeclare(exchangeDlxName, "direct");
        channel.queueDeclare(queueDlxName, true, false, true, null);
        channel.queueBind(queueDlxName, exchangeDlxName, "");

        // 消费消息
        System.out.println("[Consumer] Waiting Message...");
        channel.basicConsume(queueDlxName, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[Consumer] body = " + new String(body));
            }
        });
    }

}