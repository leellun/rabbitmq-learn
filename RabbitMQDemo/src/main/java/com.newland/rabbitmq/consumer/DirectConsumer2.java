package com.newland.rabbitmq.consumer;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class DirectConsumer2 {
    public static final String EXCHANGE="direct_exchange";
    public static final String QUEUE1="direct_update";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE1,false,false,true,null);
        channel.queueBind(QUEUE1,EXCHANGE,"update");
        channel.basicConsume(QUEUE1,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("update:"+new String(body));
            }
        });
    }
}
