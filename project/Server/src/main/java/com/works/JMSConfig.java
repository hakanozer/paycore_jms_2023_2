package com.works;

import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JMSConfig {

    @Value("${jms.broker.url}")
    private String brokerUrl;

    @Bean
    public BrokerService broker() throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector(brokerUrl);
        brokerService.setPersistent(false);
        System.out.println("Broker Success");
        return brokerService;
    }


}
