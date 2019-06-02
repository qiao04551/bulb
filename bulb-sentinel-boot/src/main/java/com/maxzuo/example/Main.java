package com.maxzuo.example;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Sentinel示例Demo，且接入控制台
 * <p>
 * Created by zfh on 2019/05/30
 */
public class Main {

    public static void main(String[] args) {
        /*
            接入Sentinel控制台，客户端启动参数：
              -Dproject.name=testApp -Dcsp.sentinel.dashboard.server=127.0.0.1:8080 -Dcsp.sentinel.api.port=8081
         */

        initFlowRules();

        baseSentinelAPI();

        // baseSentinelAnnotation();
    }

    /**
     * 配置规则
     */
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 基于Sentinel API的形式
     */
    private static void baseSentinelAPI () {
        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                // System.out.println("blocked!");
            }
        }
    }

    /**
     * 基于注解的形式
     * 文档：https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81
     */
    @SentinelResource(value = "HelloWorld")
    private static void baseSentinelAnnotation () {
        System.out.println("hello world!");
    }
}
