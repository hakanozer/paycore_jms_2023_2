package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.entities.Product;
import com.works.entities.UserData;
import com.works.repositories.ProductRepository;
import com.works.repositories.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    final JmsTemplate jmsTemplate_product;
    final JmsTemplate jmsTemplate_user;
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
    }

}
