package com.knowflow.ai.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KnowflowKnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowflowKnowledgeApplication.class, args);
    }
}
