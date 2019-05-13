package com.telekom.service.impl;


import com.telekom.service.api.JmsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


import javax.jms.*;


@Component
public class JmsServiceImpl implements JmsService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Logger logger;

    /**
     * Sends message to a topic if promoted state of tariffs was updated
     */
    @Override
    public void sendMessage() {
        jmsTemplate.send(session -> {
            logger.info("SpringLine sends message about promoted tariff update");
            TextMessage textMessage = session.createTextMessage("update");
            return textMessage;
        });
    }

}
