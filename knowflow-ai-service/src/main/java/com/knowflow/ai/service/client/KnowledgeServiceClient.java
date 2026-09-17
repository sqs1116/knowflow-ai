package com.knowflow.ai.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "knowflow-knowledge", fallback = KnowledgeServiceFallback.class)
public interface KnowledgeServiceClient {

    String SENTINEL_RESOURCE = "GET:http://knowflow-knowledge/test";

    @GetMapping("/test")
    String test();
}
