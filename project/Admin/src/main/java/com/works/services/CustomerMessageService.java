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
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerMessageService {

    final ObjectMapper objectMapper;
    final CustomerMessageRepository customerMessageRepository;
    final TinkEncDec tinkEncDec;

    @JmsListener(destination = "customerID")
    public void listener(TextMessage textMessage) throws Exception {
        String stData = textMessage.getText();
        System.out.println(stData);
        stData = tinkEncDec.decrypt(stData);
        String sessionId = textMessage.getStringProperty("sessionId");
        String agent = textMessage.getStringProperty("agent");
        if ( sessionId != null && agent != null ) {
            CustomerMessage customerMessage = objectMapper.readValue(stData, CustomerMessage.class);
            customerMessageRepository.save(customerMessage);
            System.out.println( customerMessage );
        }
    }

    public List<CustomerMessage> allMessage() {
        Sort sort = Sort.by("id").descending();
        Pageable page = PageRequest.of(0, 10, sort );
        Page<CustomerMessage> messagePage = customerMessageRepository.findAll(page);
        List<CustomerMessage> ls = messagePage.getContent();
        return ls;
    }

}