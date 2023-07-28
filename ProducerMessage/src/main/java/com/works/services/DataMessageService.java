package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.models.DataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
@RequiredArgsConstructor
public class DataMessageService {

    final JmsTemplate jmsTemplate;
    final ObjectMapper objectMapper;
    final TinkEncDec tinkEncDec;

    String sendData = "";
    public ResponseEntity send(DataMessage dataMessage) {
        dataMessage.setMid(""+System.currentTimeMillis());
        for (int i = 0; i < 1; i++) {
            dataMessage.setUserID(i);
        try {
            sendData = objectMapper.writeValueAsString(dataMessage);
            sendData = tinkEncDec.encrypt(sendData);
        }catch (Exception ex) {}
         jmsTemplate.send("message", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //System.out.println("Push Data: " + sendData );
                TextMessage textMessage = session.createTextMessage(sendData);
                if ( dataMessage.getUserID() == 100 ) {
                    //session.rollback();
                }
                return textMessage;
            }
        });
        }
        return new ResponseEntity("Send..", HttpStatus.OK);
    }

}
