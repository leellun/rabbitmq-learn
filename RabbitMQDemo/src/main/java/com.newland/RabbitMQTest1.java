package com.newland;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest1 {
    public static void main(String[] args) {
        try {
            test3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test1() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("liulun666");
        factory.setVirtualHost("/");
        factory.setHost("192.168.10.103");
        factory.setPort(5672);
        try {
            Connection connection = factory.newConnection("app:audit component:event-consumer");
            Channel channel = connection.createChannel();

//            channel.close();
//            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void test2() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://leellun:liulun666@192.168.10.103:5672/vir");
        Connection connection = factory.newConnection();
    }
    private static void test3() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("liulun666");
        factory.setVirtualHost("/");
        factory.setHost("192.168.10.103");
        factory.setPort(5672);
        try {
            String exchangeName="exchange2";
            String routingKey="routingKey";
            Connection connection = factory.newConnection("app:audit component:event-consumer");
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, "direct", true);
            String queueName = channel.queueDeclare().getQueue();
            System.out.println(queueName);
            channel.queueBind(queueName, exchangeName, routingKey);
//            channel.basicConsume(queueName,true,new ConsumerCallback(channel));
            channel.addReturnListener(new ReturnListener() {
                public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("ReturnListener===>"+new String(body));
                }
            });
            channel.basicPublish(exchangeName,routingKey,null,"sdfsdfsd".getBytes());
            channel.queueDelete(queueName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    private static void test4() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("liulun666");
        factory.setVirtualHost("/");
        factory.setHost("192.168.10.103");
        factory.setPort(5672);
        try {
            Connection connection = factory.newConnection("app:audit component:event-consumer");
            Channel channel = connection.createChannel();
            AMQP.Queue.DeclareOk response = channel.queueDeclarePassive("sdfsdfsdfssf");
            // returns the number of messages in Ready state in the queue
            response.getMessageCount();
            // returns the number of consumers the queue has
            response.getConsumerCount();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
