package com.newland.rabbitmq.consumer;

import com.newland.rabbitmq.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeadersConsumer {
    public static final String QUEUE1="headersqueue";
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        Map<String,Object> headers=new HashMap<String, Object>();
        headers.put("test","testheaders");
        channel.queueDeclare(QUEUE1,true,false,false,headers);
        channel.basicConsume(QUEUE1,true,headers,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("INSERT/DELETE:"+new String(body));
            }
        });
    }
}
