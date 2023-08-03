package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.models.CustomerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final JmsTemplate jmsTemplate;
    final ObjectMapper objectMapper;
    final HttpServletRequest req;

    String stData = "";
    public boolean send(CustomerMessage customerMessage) {
        String sessionID = req.getSession().getId();
        try {
            String uuid = UUID.randomUUID().toString();
            customerMessage.setMid(uuid);
            customerMessage.setCid(100l);
            stData = objectMapper.writeValueAsString(customerMessage);
        }catch (Exception ex) {}
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(stData);
                textMessage.setStringProperty("sessionId",sessionID );
                return textMessage;
            }
        };
        jmsTemplate.send("customerID", messageCreator);
        return true;
    }

}