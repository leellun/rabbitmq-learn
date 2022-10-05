package com.newland.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnectionUtil {
    public static Connection getConnection() {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri("amqp://leellun:123456@192.168.66.11:5672/test_vhost");
//        // 提供一个连接名称
//        Connection conn = factory.newConnection("app:audit component:event-consumer");
//        return conn;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("123456");
        factory.setVirtualHost("/");
        factory.setHost("192.168.66.70");
        factory.setPort(5672);
        try {
            Connection connection = factory.newConnection();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }
    public static Connection newConnection(String host,int port) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("123456");
        factory.setVirtualHost("/");
        factory.setHost(host);
        factory.setPort(port);
        try {
            Connection connection = factory.newConnection();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }
}
