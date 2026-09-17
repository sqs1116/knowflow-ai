package com.knowflow.ai.knowledge.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.knowflow.ai.knowledge.controller.KnowledgeTestController;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Collections;

@Configuration
public class SentinelRuleConfig {

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFlowRules() {
        FlowRule knowledgeTestRule = new FlowRule();
        knowledgeTestRule.setResource(KnowledgeTestController.SENTINEL_RESOURCE);
        knowledgeTestRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        knowledgeTestRule.setCount(10);
        knowledgeTestRule.setLimitApp("default");

        FlowRuleManager.loadRules(Collections.singletonList(knowledgeTestRule));
    }
}
