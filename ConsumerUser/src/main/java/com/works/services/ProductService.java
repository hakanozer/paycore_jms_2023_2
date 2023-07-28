package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductService {

    final JmsTemplate jmsTemplate;
    final ObjectMapper objectMapper;
    final RestTemplate restTemplate;

    String stData = "";
    public void sendProduct() {
        Product product = new Product();
        Random random = new Random();
        long pid = random.nextInt(150) + 1;
        product.setPid(pid);
        product.setTitle("Masa");
        product.setPrice(10000L);

        try {
            stData = objectMapper.writeValueAsString(product);
            jmsTemplate.send("product", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    System.out.println("pid: " + pid);
                    TextMessage textMessage = (TextMessage) session.createTextMessage(stData);
                    //textMessage.setJMSDeliveryTime(5000);
                    String url = "https://dummyjson.com/products/"+pid;
                    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                    if(response.getStatusCodeValue() == 200) {
                        return textMessage;
                    }
                    textMessage.setText("");
                    System.out.println("Product Not Found :" + pid);
                    return textMessage;
                }
            });
        }catch (Exception ex) {}

    }
}
