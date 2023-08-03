package com.works;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class SiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

}
