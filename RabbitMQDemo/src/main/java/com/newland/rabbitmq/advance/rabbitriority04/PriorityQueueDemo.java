package com.newland.rabbitmq.advance.rabbitriority04;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证优先级队列
 */
public class PriorityQueueDemo {
    /** 交换器名称 */
    private final String EXCHANGE_NAME = "exchange-priority";
    /** 队列名称 */
    private final String QUEUE_NAME = "queue-priority" ;

    public static void main(String[] args) throws Exception {
        PriorityQueueDemo demo = new PriorityQueueDemo();
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
        Map<String,Object> argss =new HashMap<String,Object>() ;
        // 设置队列的优先级为10
        argss.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE_NAME, true, false, true, argss) ;
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 发送消息
        System.out.println("[Sender] Send Message...");
        for(int i = 0; i < 5; i++) {
            // 为每个消息设置随机优先级，优先级位于 0~10
            int priority = i;
            String message = "Priority Message priority=" + priority;
            // 设置消息的优先级
            AMQP.BasicProperties msgProps = new AMQP.BasicProperties.Builder()
                    .priority(priority).build();
            channel.basicPublish(EXCHANGE_NAME, "",
                    msgProps, message.getBytes());
            System.out.println("[Sender] message = " + message);
        }

        // 启动消费者
        consumer();

        // 释放资源
        channel.close();
        connection.close();
    }

    /**
     * 消费者
     * @throws Exception
     */
    private void consumer() throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 创建信道
        final Channel channel = connection.createChannel();

        // 声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 声明队列
        Map<String,Object> argss =new HashMap<String,Object>() ;
        // 设置队列的优先级为10
        argss.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE_NAME, true, false, true, argss) ;
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 消费消息
        System.out.println("[Consumer] Waiting Message...");
        channel.basicConsume(QUEUE_NAME, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    // 模拟费时超过
                    System.out.println("[Consumer] message = " + new String(body));
                    Thread.sleep(1000);

                    // 手动发送ACK确认消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
