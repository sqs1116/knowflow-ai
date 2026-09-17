package com.knowflow.ai.service.client;

import org.springframework.stereotype.Component;

@Component
public class KnowledgeServiceFallback implements KnowledgeServiceClient {

    @Override
    public String test() {
        return "knowledge service unavailable";
    }
}
