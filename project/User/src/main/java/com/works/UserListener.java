package com.works;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.TextMessage;

@Configuration
public class UserListener {

    @JmsListener(destination = "customerID")
    public void listener(TextMessage textMessage) throws Exception {
        String stData = textMessage.getText();
        System.out.println(stData);
    }

    @JmsListener(destination = "newCustomer")
    public void listener_1(TextMessage textMessage) throws Exception {
        String stData = textMessage.getText();
        System.out.println(stData);
    }


}
