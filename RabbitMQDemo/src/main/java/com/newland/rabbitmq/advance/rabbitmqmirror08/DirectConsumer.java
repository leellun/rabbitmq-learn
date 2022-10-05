package com.newland.rabbitmq.advance.rabbitmqmirror08;

import com.newland.rabbitmq.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class DirectConsumer {
    public static final String EXCHANGE="mirror_exchange";
    public static final String QUEUE1="mirror_insert";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.newConnection("192.168.66.70",5673);
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE1,true,false,true,null);
        channel.exchangeDeclare(EXCHANGE,"direct",true,true,null);
        channel.queueBind(QUEUE1,EXCHANGE,"insert");
        channel.basicConsume(QUEUE1,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("INSERT/DELETE:"+new String(body));
            }
        });
    }
}
