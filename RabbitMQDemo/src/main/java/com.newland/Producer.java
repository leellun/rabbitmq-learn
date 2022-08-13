package com.newland;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author Mqs
 * @Date 2018/10/27 22:05
 * @Desc
 */
public class Producer {
    public static void main(String[] args) throws Exception{
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("liulun666");
        factory.setVirtualHost("/");
        factory.setHost("192.168.10.103");
        // 2、创建连接
        Connection connection = factory.newConnection();
        // 3、获取通道
        Channel channel = connection.createChannel();

        String exchangeName = "exchange2";
        String routingKey = "return.key";
        String routingKeyError = "return.error.key";

        String msg = "send message test return mandatory ";

        // 用于监听不可达的消息
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText,
                                     String exchange, String routingKey,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("======= handle ======= return ========");
                System.out.println("replyCode: " + replyCode);
                System.out.println("replyText: " + replyText);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                System.out.println("properties: " + properties);
                System.out.println("body: " + new String(body));

            }
        });

        // 可以正常的路由到routingkey
//        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        // 不可以正常的路由到routingkey mandatory 为ture
        // TODO 这个会被上面的监听器监听到，会打印要输出的信息
        channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());
        // 不可以正常的路由到routingkey mandatory 为false
        // TODO 这个不会打印任何消息，会直接删除这个消息
//        channel.basicPublish(exchangeName, routingKeyError, false, null, msg.getBytes());

    }
}
