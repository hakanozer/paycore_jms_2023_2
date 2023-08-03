package com.works.controllers;

import com.works.services.CustomerMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    final CustomerMessageService customerMessageService;

    @GetMapping("/")
    public String admin(Model model) {
        model.addAttribute("all", customerMessageService.allMessage());
        return "admin";
    }

}
