package com.works.controllers;

import com.works.services.GlobalException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
public class AppBeans {

    @Bean
    public DefaultJmsListenerContainerFactory listenerContainerFactory(ConnectionFactory connectionFactory, GlobalException globalException) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        defaultJmsListenerContainerFactory.setErrorHandler(globalException);
        System.out.println("DefaultJmsListenerContainerFactory Call");
        return defaultJmsListenerContainerFactory;
    }

    @Bean
    JmsListenerContainerFactory<?> deJmsContainerFactory(ConnectionFactory connectionFactory, GlobalException globalException) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(globalException);
        return factory;
    }

}
