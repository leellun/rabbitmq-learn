package com.newland.springrabbit;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class MqProducerConfig {
    public static void main(String[] args){
        ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
        AmqpTemplate rabbitTemplate= (AmqpTemplate) context.getBean("rabbitTemplate");
        rabbitTemplate.convertAndSend("exchange1","aa", "sdfsdfsdf2");
    }

}


