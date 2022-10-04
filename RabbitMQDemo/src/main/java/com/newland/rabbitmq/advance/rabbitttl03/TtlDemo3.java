package com.newland.rabbitmq.advance.rabbitttl03;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * 为队列设置 TTL，过期后队列将自动删除
 */
public class TtlDemo3 {
    /**
     * 交换器名称
     */
    private final String EXCHANGE_NAME = "exchange-ttl3";
    /**
     * 队列名称
     */
    private final String QUEUE_NAME = "queue-ttl3";

    public static void main(String[] args) throws Exception {
        TtlDemo3 demo = new TtlDemo3();
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
        Map<String, Object> argss = new HashMap<String, Object>();
        argss.put("x-expires", 12000); // 设置过期时间为 6 秒
        channel.queueDeclare(QUEUE_NAME, true, false, false, argss);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 发送消息
        System.out.println("[Sender] Send Message...");
        channel.basicPublish(EXCHANGE_NAME, "",null, "ttl message".getBytes());
        System.out.println("[Sender] message = “ttl message”");

        // 关闭连接
        channel.close();
        connection.close();
    }

}
