package com.telekom.config;

import java.util.Arrays;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jca.cci.connection.SingleConnectionFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
public class MessagingConfiguration {

    private static final String ORDER_TOPIC = "testTopic";
    @Bean
    public JndiObjectFactoryBean solicitudesConnectionFactory() {
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("java:jboss/exported/jms/RemoteConnectionFactory");
        jndi.setLookupOnStartup(true);
        jndi.setProxyInterface(ConnectionFactory.class);

        return jndi;
    }


    @Bean
    public UserCredentialsConnectionFactoryAdapter ConnectionFactory(JndiObjectFactoryBean jndi) {
        UserCredentialsConnectionFactoryAdapter  adapter= new UserCredentialsConnectionFactoryAdapter();
        adapter.setPassword("ekakoc");
        adapter.setUsername("ekakoc2");
        adapter.setTargetConnectionFactory( (ConnectionFactory) jndi.getObject());
        return adapter;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter adapter) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(adapter);
        cachingConnectionFactory.setSessionCacheSize(1000);
        cachingConnectionFactory.setReconnectOnException(true);
        return cachingConnectionFactory;
    }


    /*
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {

        ConnectionFactory connectionFactory = InitialContext.doLookup("java:/ConnectionFactory");
        return connectionFactory;
    }
*/
    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory cf) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(cf);
        template.setDefaultDestinationName(ORDER_TOPIC);
       template.setPubSubDomain(true); //creates topic
        return template;
    }
}


