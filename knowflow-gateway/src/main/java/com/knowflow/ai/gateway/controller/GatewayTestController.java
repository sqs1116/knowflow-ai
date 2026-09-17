package com.knowflow.ai.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayTestController {

    @GetMapping("/test")
    public String test() {
        return "knowflow-gateway is running";
    }

    @GetMapping("/api/health/gateway")
    public String health() {
        return "UP";
    }
}
