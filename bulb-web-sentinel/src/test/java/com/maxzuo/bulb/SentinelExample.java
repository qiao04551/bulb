package com.maxzuo.bulb;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sentinel示例Demo，定义资源和规则。
 * <pre>
 *   1.定义资源的五种方式（主流框架适配、抛出异常的方式定义资源、返回布尔值方式定义资源、注解方式定义资源、异步调用支持）
 *   2.规则的种类（流量控制规则、熔断降级规则、系统保护规则、来源访问控制规则 和 热点参数规则）
 *   3.接入控制台，客户端启动参数：
 *     -Dproject.name=testApp -Dcsp.sentinel.dashboard.server=127.0.0.1:8080 -Dcsp.sentinel.api.port=8081
 * </pre>
 * Created by zfh on 2019/05/30
 */
public class SentinelExample {

    public static void main(String[] args) {
        String resourceName = "HelloWorld";
        initFlowQpsRule(resourceName);
        invoke();
    }

    /**
     * 定义流量控制规则 —— QPS模式
     */
    private static void initFlowQpsRule(String resourceName) {
        List<FlowRule> rules = new ArrayList<>(4);
        FlowRule rule = new FlowRule();
        rule.setCount(5);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setResource(resourceName);
        rule.setStrategy(0);
        rule.setControlBehavior(0);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        /// 覆盖上一次规则
        // FlowRuleManager.loadRules(Collections.singletonList(new FlowRule()));
    }

    /**
     * 定义流量控制规则 —— 线程数模式
     */
    private static void initFlowThreadRule(String resourceName) {
        List<FlowRule> rules = new ArrayList<>(4);
        FlowRule rule = new FlowRule();
        rule.setCount(5);
        rule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        rule.setLimitApp("default");
        rule.setControlBehavior(0);
        rule.setResource(resourceName);
        rule.setStrategy(0);
        rules.add(rule);

        FlowRuleManager.loadRules(rules);
    }

    /**
     * 熔断降级规则 —— 响应时间
     */
    private static void initDegradeRule (String resourceName) {
        DegradeRule rule = new DegradeRule();
        rule.setResource(resourceName);
        // 资源的平均响应时间的阀值
        rule.setCount(2);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 熔断的时间
        rule.setTimeWindow(5);
        DegradeRuleManager.loadRules(Collections.singletonList(rule));
    }


    /**
     * 定义资源方式一：主流框架适配
     */

    /**
     * 定义资源方式二：抛出异常的方式定义资源
     * <pre>
     *   SphU 包含了 try-catch 风格的 API。用这种方式，当资源发生了限流之后会抛出 BlockException。
     *   这个时候可以捕捉异常，进行限流之后的逻辑处理。
     * </pre>
     */
    private static void invoke() {
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                // 被保护的逻辑
                System.out.println("welcome defineResource");

                Thread.sleep(2);
            } catch (Exception ex) {
                // 处理被流控的逻辑
                // System.out.println("blocked!");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

    /**
     * 定义资源方式三：返回布尔值方式定义资源
     * <pre>
     *   SphO 提供 if-else 风格的 API。用这种方式，当资源发生了限流之后会返回 false，这个时候可以根据返回值，
     *   进行限流之后的逻辑处理。
     * </pre>
     */
    private static void baseBooleanValue() {
        while (true) {
            if (SphO.entry("HelloWorld")) {
                try {
                    // 被保护的业务逻辑
                    System.out.println("hello baseBooleanValue");
                } finally {
                    SphO.exit();
                }
            } else {
                // 资源访问阻止，被限流或被降级
                // 进行相应的处理操作
            }
        }
    }

    /**
     * 定义资源方式四：注解方式定义资源
     * <pre>
     *   Sentinel 支持通过 @SentinelResource 注解定义资源并配置blockHandler和fallback函数来进行限流之后的处理
     *   注意 blockHandler 函数会在原方法被限流/降级/系统保护的时候调用，而 fallback 函数仅会在原方法被降级时作为
     *   fallback 方法，其余时候不会被调用。
     *
     *   Sentinel注解支持文档：https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81
     * </pre>
     */

    /**
     * 定义资源方式五：异步调用支持
     */
}
