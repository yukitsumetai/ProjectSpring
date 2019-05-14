package com.telekom.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import javax.jms.ConnectionFactory;

@Configuration
public class MessagingConfiguration {

    private static final String QUEUE = "testTopic";

    @Bean
    public JndiObjectFactoryBean jndiObjectFactoryBean() {
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("java:jboss/exported/jms/RemoteConnectionFactory");
        jndi.setLookupOnStartup(true);
        jndi.setProxyInterface(ConnectionFactory.class);
        return jndi;
    }

    @Bean
    public UserCredentialsConnectionFactoryAdapter ConnectionFactory(JndiObjectFactoryBean jndi) {
        UserCredentialsConnectionFactoryAdapter adapter= new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername("ekakoc");
        adapter.setPassword("ekakoc2");
        adapter.setTargetConnectionFactory( (ConnectionFactory)jndi.getObject());
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

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory cf) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(cf);
        template.setDefaultDestinationName(QUEUE);
       template.setPubSubDomain(true); //creates topic
        return template;
    }
}


