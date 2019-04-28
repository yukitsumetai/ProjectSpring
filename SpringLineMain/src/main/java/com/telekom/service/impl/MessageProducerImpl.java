package com.telekom.service.impl;


import com.telekom.model.dto.TariffDto;
import com.telekom.model.entity.Tariff;
import com.telekom.service.api.MessageProducer;
import com.telekom.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


import javax.jms.*;
import java.util.List;


@Component
public class MessageProducerImpl implements MessageProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public void sendMessage() {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("test");
                return textMessage;
            }
        });
    }

}
