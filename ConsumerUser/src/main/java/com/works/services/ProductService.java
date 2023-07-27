package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
@RequiredArgsConstructor
public class ProductService {

    final JmsTemplate jmsTemplate;
    final ObjectMapper objectMapper;

    String stData = "";
    public void sendProduct() {
        Product product = new Product();
        product.setPid(100L);
        product.setTitle("Masa");
        product.setPrice(10000L);

        try {
            stData = objectMapper.writeValueAsString(product);
            jmsTemplate.send("product", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = (TextMessage) session.createTextMessage(stData);
                    return textMessage;
                }
            });
        }catch (Exception ex) {}

    }
}
