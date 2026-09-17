package com.knowflow.ai.service.controller;

import com.knowflow.ai.service.client.KnowledgeServiceClient;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class AiTestController {

    private final KnowledgeServiceClient knowledgeServiceClient;

    public AiTestController(KnowledgeServiceClient knowledgeServiceClient) {
        this.knowledgeServiceClient = knowledgeServiceClient;
    }

    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/test")
    public String test() {
        boolean locked = false;
        String lockKey = "lock:document";
        RLock lock = redissonClient.getLock(lockKey);
        try {
            locked = lock.tryLock();
            if (!locked) {
                return "系统繁忙，请稍后重试";
            }
            // ====================
            // 处理业务
            // ====================

            System.out.println("执行业务");

            Thread.sleep(15000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return "knowflow-ai-service is running";
    }

    @GetMapping("/feign/knowledge-test")
    public String callKnowledgeTest() {
        return "knowflow-ai-service -> " + knowledgeServiceClient.test();
    }
}
