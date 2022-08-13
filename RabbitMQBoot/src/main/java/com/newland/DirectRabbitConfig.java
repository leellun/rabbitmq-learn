package com.newland;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DirectRabbitConfig {
    @Bean
    public Queue TestDirectQueue() {
        return new Queue("TestDirectQueue",true);
    }

    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange",true,false);
    }

    @Bean
    Binding bindingDirect(Queue queue,DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("TestDirectRouting");
    }
}