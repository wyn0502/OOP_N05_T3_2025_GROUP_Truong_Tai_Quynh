package com.example.zoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorControllerCustom {

    @GetMapping("/505")
    public String accessDenied() {
        return "error/505"; 
    }
}
