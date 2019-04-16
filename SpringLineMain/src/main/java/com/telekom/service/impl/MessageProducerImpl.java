package com.telekom.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


import javax.jms.*;
import java.util.Queue;


@Component
public class MessageProducerImpl {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage() {

        jmsTemplate.send(new MessageCreator(){
            @Override
            public Message createMessage(Session session) throws JMSException{
                TextMessage objectMessage = session.createTextMessage("test");
                return objectMessage;
            }
        });
    }

}
