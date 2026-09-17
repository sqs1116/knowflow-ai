package com.knowflow.ai.service.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.knowflow.ai.service.client.KnowledgeServiceClient;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
public class SentinelRuleConfig {

    @PostConstruct
    public void initializeDegradeRules() {
        DegradeRule knowledgeServiceRule = new DegradeRule();
        knowledgeServiceRule.setResource(KnowledgeServiceClient.SENTINEL_RESOURCE);
        knowledgeServiceRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        knowledgeServiceRule.setCount(0.5);
        knowledgeServiceRule.setMinRequestAmount(2);
        knowledgeServiceRule.setStatIntervalMs(10_000);
        knowledgeServiceRule.setTimeWindow(5);

        DegradeRuleManager.loadRules(Collections.singletonList(knowledgeServiceRule));
    }
}
