package com.works.configs;

import lombok.RequiredArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
@RequiredArgsConstructor
public class JMSConfig {

    final ProductListner productListner;


    @Value("${jms.broker.url}")
    private String brokerUrl;

    @Value("${jms.queue.product}")
    private String product;


    @Bean
    public Destination destination_product() {
        return new ActiveMQQueue(product);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
    }

    @Bean
    public MessageListenerContainer productMessageListenerContainer(
            ConnectionFactory connectionFactory, Destination destination_product
    ) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setDestination(destination_product);
        messageListenerContainer.setMessageListener( productListner );
        return messageListenerContainer;
    }

}
