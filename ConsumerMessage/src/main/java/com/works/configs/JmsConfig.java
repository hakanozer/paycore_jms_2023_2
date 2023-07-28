package com.works.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.models.DataMessage;
import com.works.services.TinkEncDec;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

import javax.jms.Message;
import javax.jms.TextMessage;

@Configuration
@RequiredArgsConstructor
public class JmsConfig {

    final TinkEncDec tinkEncDec;
    final ObjectMapper objectMapper;

    @JmsListener(destination = "message")
    public void pullMessage(
            @Headers MessageHeaders messageHeaders,
            Message message
            ) throws Exception {
        if ( message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String stData = textMessage.getText();
            stData = tinkEncDec.decrypt(stData);
            DataMessage dataMessage = objectMapper.readValue(stData, DataMessage.class);
            //System.out.println( dataMessage );
            System.out.println( dataMessage.getMid() );
            System.out.println( System.currentTimeMillis() );
            //System.out.println( dataMessage.getMid() );
            //System.out.println( dataMessage.getUserID() );
            //System.out.println( dataMessage.getMessage() );
        }
    }

}
