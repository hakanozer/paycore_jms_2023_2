package com.works.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.entities.Product;
import com.works.entities.UserData;
import com.works.models.Customer;
import com.works.repositories.ProductRepository;
import com.works.repositories.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    final JmsTemplate jmsTemplate_product;
    final JmsTemplate jmsTemplate_user;
    final JmsTemplate jmsTemplate_customer;

    final ObjectMapper objectMapper;
    final ProductRepository productRepository;
    final UserDataRepository userDataRepository;

    public void send(Product product) {
        UserData userData = new UserData();
        userData.setName(UUID.randomUUID().toString());
        userData.setAge( new Random().nextInt(80) + 18);
        productRepository.save(product);
        userDataRepository.save(userData);
        String stData = "";
        String stUserData = "";
        try {
            stData = objectMapper.writeValueAsString(product);
            stUserData = objectMapper.writeValueAsString(userData);
        }catch (Exception ex) {}
        //System.out.println(stData);
        jmsTemplate_product.convertAndSend(stData);
        jmsTemplate_user.convertAndSend(stUserData);
        try {
            customerMessageSend();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    String stDataCustomer = "";
    public void customerMessageSend() throws JsonProcessingException {
        System.out.println("customerMessageSend Call");
        Customer customer = Customer.builder()
                .cid(UUID.randomUUID().toString())
                .name("Name")
                .email("ali@mail.com")
                .password(""+new Random().nextInt(234234) + 12234)
                .build();
        stDataCustomer = objectMapper.writeValueAsString(customer);
        jmsTemplate_customer.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = session.createTextMessage(stDataCustomer);
                String messageID = UUID.randomUUID().toString();
                message.setJMSMessageID(messageID);
                System.out.println( messageID );
                return message;
            }
        });
    }

}
