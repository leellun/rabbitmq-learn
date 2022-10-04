package com.newland.rabbitmq.advance.returnlistener;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 使用 channel.addReturnListener() 添加 ReturnListener 监听器，监听
 * 没有成功路由给队列的消息。
 */
public class ReturnListener1 {
    private static final String EXCHANGE_NAME = "exchange_" +
            ReturnListener1.class.getSimpleName();

    /**
     * 发送消息
     */
    private void sender() throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {
                System.out.println("<< " + new String(body));
            }
        });

        // 发送消息
        System.out.println("[Send] Sending Message...");
        channel.basicPublish(EXCHANGE_NAME, "route_key",
                true, MessageProperties.PERSISTENT_TEXT_PLAIN, "hello wrold".getBytes());
        Thread.sleep(Long.MAX_VALUE);
        // 释放资源
        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws Exception {
        ReturnListener1 demo = new ReturnListener1();
        demo.sender();
    }

}
