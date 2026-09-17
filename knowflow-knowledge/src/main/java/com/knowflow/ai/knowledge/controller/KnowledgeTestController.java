package com.knowflow.ai.knowledge.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.knowflow.ai.knowledge.config.OperLog;
import com.knowflow.ai.knowledge.config.RepeatSubmit;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class KnowledgeTestController {
    @Resource
    private RedissonClient redissonClient;


    public static final String SENTINEL_RESOURCE = "GET:/api/knowledge/test";

    @GetMapping({"/test", "/api/knowledge/test"})
    @RepeatSubmit(interval = 5000)
    public String  test() {
            return "knowflow-ai-service is running";
    }

    public ResponseEntity<String> handleTestBlocked(BlockException exception) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("请求过于频繁，请稍后再试");
    }
}
