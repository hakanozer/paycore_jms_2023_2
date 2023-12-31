package com.works.configs;

import com.works.services.GlobalException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.web.client.RestTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class AppBeans {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DefaultJmsListenerContainerFactory listenerContainerFactory(ConnectionFactory connectionFactory, GlobalException globalException) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        defaultJmsListenerContainerFactory.setErrorHandler(globalException);
        System.out.println("DefaultJmsListenerContainerFactory Call");
        return defaultJmsListenerContainerFactory;
    }

}
