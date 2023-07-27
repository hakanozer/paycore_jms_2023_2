package com.works.configs;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
public class JMSConfig {

    @Value("${jms.broker.url}")
    private String brokerUrl;

    @Value("${jms.queue.product}")
    private String product;

    @Value("${jms.queue.user}")
    private String user;

    @Bean
    public Destination destination_product() {
        return new ActiveMQQueue(product);
    }

    @Bean
    public Destination destination_user() {
        return new ActiveMQQueue(user);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory( new ActiveMQConnectionFactory(brokerUrl));
    }

    @Bean
    public JmsTemplate jmsTemplate_product( ConnectionFactory connectionFactory, Destination destination_product ) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(destination_product);
        return jmsTemplate;
    }

    @Bean
    public JmsTemplate jmsTemplate_user( ConnectionFactory connectionFactory, Destination destination_user ) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(destination_user);
        return jmsTemplate;
    }


}
