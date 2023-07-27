package com.works;

import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;

@Component
@RequiredArgsConstructor
public class UserListener {

    final ProductService productService;

    @JmsListener(destination = "user")
    public void userFnc(Message message) {
        productService.sendProduct();
        try {
            if ( message instanceof TextMessage ) {
                TextMessage textMessage = (TextMessage) message;
                String data = textMessage.getText();
                System.out.println( data );
            }
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

}
