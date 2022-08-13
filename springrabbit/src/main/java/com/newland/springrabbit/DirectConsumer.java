package com.newland.springrabbit;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
public class DirectConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("===>"+new String(message.getBody()));
    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {

    }

    @Override
    public void onMessageBatch(List<Message> messages) {

    }
}
