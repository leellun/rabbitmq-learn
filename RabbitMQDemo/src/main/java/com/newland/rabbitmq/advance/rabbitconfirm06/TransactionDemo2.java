package com.newland.rabbitmq.advance.rabbitconfirm06;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 验证 RabbitMQ 事务机制，发送消息时异常，调用 channel.txRollback() 进行回滚
 */
public class TransactionDemo2 {
    /**
     * 交换器名称
     */
    private final String EXCHANGE_NAME = "exchange-transaction";
    /**
     * 队列名称
     */
    private final String QUEUE_NAME = "queue-transaction";

    public static void main(String[] args) throws Exception {
        TransactionDemo2 demo = new TransactionDemo2();
        demo.consumer();
        demo.sender();
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

        // 声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        try {
            // 开启事务
            channel.txSelect();
            // 发送消息
            String message = "transaction message";
            channel.basicPublish(EXCHANGE_NAME, "",
                    false, null, message.getBytes());
            // 模拟一个异常
            System.out.println(1 / 0);
            System.out.println("[Send] Send message ‘" + message + "’");
            // 提交消息
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
            channel.txRollback();
        }

        // 关闭连接
        channel.close();
        connection.close();
    }

    /**
     * 消费者
     *
     * @throws Exception
     */
    private void consumer() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 创建信道
        Channel channel = connection.createChannel();

        // 声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true,
                false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 消费消息
        System.out.println("[Consumer] Waiting for a message....");
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[Consumer] body = " + new String(body));
            }
        });
    }

}
