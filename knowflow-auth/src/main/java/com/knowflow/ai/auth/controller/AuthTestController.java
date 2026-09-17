package com.knowflow.ai.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {

    @GetMapping("/test")
    public String test() {
        return "knowflow-auth is running";
    }
}
