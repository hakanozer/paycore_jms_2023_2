package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.entities.CustomerMessage;
import com.works.repositories.CustomerMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerMessageService {

    final ObjectMapper objectMapper;
    final CustomerMessageRepository customerMessageRepository;
    final TinkEncDec tinkEncDec;
    final JmsTemplate jmsTemplate;

    String stData = "";
    @JmsListener(destination = "customerID")
    //@SendTo("newCustomer")
    public void listener(
            TextMessage textMessage,
            @Headers MessageHeaders headers
            ) throws Exception {
        stData = textMessage.getText();
        System.out.println(stData);

        // headers
        Set<String> keys = headers.keySet();
        for ( String key : keys ) {
            System.out.println( key + " - " + headers.get(key) );
        }

        stData = tinkEncDec.decrypt(stData);
        String sessionId = textMessage.getStringProperty("sessionId");
        String agent = textMessage.getStringProperty("agent");
        String messageID = textMessage.getJMSMessageID();
        Long time = System.currentTimeMillis();
        System.out.println( messageID );
        System.out.println( time );
        if ( sessionId != null && agent != null ) {
            CustomerMessage customerMessage = objectMapper.readValue(stData, CustomerMessage.class);
            customerMessageRepository.save(customerMessage);
            System.out.println( customerMessage );
        }

        jmsTemplate.send("newCustomer", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage1 = session.createTextMessage(stData);
                return textMessage1;
            }
        });
    }

    public List<CustomerMessage> allMessage() {
        Sort sort = Sort.by("id").descending();
        Pageable page = PageRequest.of(0, 10, sort );
        Page<CustomerMessage> messagePage = customerMessageRepository.findAll(page);
        List<CustomerMessage> ls = messagePage.getContent();
        return ls;
    }

}