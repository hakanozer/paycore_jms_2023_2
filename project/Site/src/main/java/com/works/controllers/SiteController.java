package com.works.controllers;

import com.works.models.CustomerMessage;
import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SiteController {

    final CustomerService customerService;

    @GetMapping("/")
    public String site() {
        return "site";
    }

    @PostMapping("/send")
    public String send(CustomerMessage customerMessage) {
        customerService.send(customerMessage);
        return "redirect:/";
    }

}
