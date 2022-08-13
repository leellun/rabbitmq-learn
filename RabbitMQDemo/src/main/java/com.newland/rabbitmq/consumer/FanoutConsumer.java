package com.newland.rabbitmq.consumer;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class FanoutConsumer {
    public static final String EXCHANGE="fanout_exchange";
    public static final String QUEUE1="fanout_queue1";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE1,false,false,true,null);
        channel.exchangeDeclare(EXCHANGE,"fanout",true);
        channel.queueBind(QUEUE1,EXCHANGE,"");
        channel.basicConsume(QUEUE1,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1:"+new String(body));
            }
        });
    }
}
