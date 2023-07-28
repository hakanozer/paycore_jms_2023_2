package com.works;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.models.Customer;
import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.soap.Text;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserListener {

    final ProductService productService;
    final ObjectMapper objectMapper;

    @JmsListener(destination = "user")
    public void userFnc_1(Message message) {
        productService.sendProduct();
        try {
            if ( message instanceof TextMessage ) {
                TextMessage textMessage = (TextMessage) message;
                String data = textMessage.getText();
                System.out.println( "userFnc_1" + data );
            }
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /*
    @JmsListener(destination = "user")
    public void userFnc_2(Message message) {
        productService.sendProduct();
        try {
            if ( message instanceof TextMessage ) {
                TextMessage textMessage = (TextMessage) message;
                String data = textMessage.getText();
                System.out.println( "userFnc_2" + data );
            }
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
     */

    @JmsListener(destination = "customer")
    public void customerListener(//@Payload Customer customer,
                                 @Headers MessageHeaders messageHeaders,
                                 Message message) throws JMSException, JsonProcessingException {
        Set<Map.Entry<String, Object>> entries = messageHeaders.entrySet();
        for(Map.Entry<String, Object> item : entries) {
            System.out.println( item.getKey() + " - " + item.getValue() );
        }
        TextMessage textMessage = (TextMessage) message;
        System.out.println( "ID: " + message.getJMSMessageID() );
        Customer customer = objectMapper.readValue(textMessage.getText(), Customer.class);
        System.out.println( "Pull Customer: " + customer );

    }


}
