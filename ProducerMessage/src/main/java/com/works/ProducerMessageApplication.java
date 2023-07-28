package com.works;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ProducerMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerMessageApplication.class, args);
    }

}
