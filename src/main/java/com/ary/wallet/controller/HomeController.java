package com.ary.wallet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return "Welcome to Trading Platform";
    }

    // This is to test if AppConfig.java is working or not.
    @GetMapping("/api")
    public String secure() {
        return "Welcome to Trading Platform Secured";
    }
}
