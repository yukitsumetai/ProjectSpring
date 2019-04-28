package com.telekom;


import org.slf4j.Logger;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(name = "Listener1", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "testTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class Subscriber implements MessageListener {

    @Inject
    @Push
    private PushContext push;

    @Inject
    TariffsConsumer tariffsConsumer;




    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
           // logger.debug("JMS messaje recieved");
            tariffsConsumer.getTariffs();
            push.send("update");
    }





}

