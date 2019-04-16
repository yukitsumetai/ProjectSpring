package com.telekom;


import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.*;


@Stateless
@LocalBean
public class Producer {

    @Resource(name = "java:jboss/exported/jms/RemoteConnectionFactory")
    private TopicConnectionFactory connectionFactory;

    @Resource(name = "java:jboss/exported/jms/topic/test")
    private Destination destination;

    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void produceMessage() {
        try {
            TopicConnection connection = connectionFactory.createTopicConnection("ekakoc", "moon2207");
            TopicSession session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("Hello");
            connection.close();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
