package com.knowflow.ai.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.knowflow.ai.auth.mapper")
public class KnowflowAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowflowAuthApplication.class, args);
    }
}
