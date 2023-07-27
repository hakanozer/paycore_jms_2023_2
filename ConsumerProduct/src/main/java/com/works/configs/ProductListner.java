package com.works.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Configuration
@RequiredArgsConstructor
public class ProductListner implements MessageListener {

    final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message) {
        if ( message instanceof TextMessage ) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String messageID = textMessage.getJMSMessageID();
                long timestampTime = textMessage.getJMSTimestamp();
                //long deliveryTime = textMessage.getJMSDeliveryTime();
                String messageTxt = textMessage.getText();
                //System.out.println(messageID);
                //System.out.println(timestampTime);
                //System.out.println(deliveryTime);
                //System.out.println(messageTxt);
                Product product = objectMapper.readValue(messageTxt, Product.class);
                System.out.println( product );
            }catch (Exception ex) {
                System.err.println("onMessage: " + ex);
            }
        }
    }

}
