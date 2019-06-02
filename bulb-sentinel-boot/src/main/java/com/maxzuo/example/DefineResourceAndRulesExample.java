package com.maxzuo.example;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
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
public class DefineResourceAndRulesExample {

    public static void main(String[] args) {
        initFlowQpsRule();

        // baseThrowException();

        // baseBooleanValue();
    }

    /**
     * 通过代码定义流量控制规则
     * 流量控制规则wiki：https://github.com/alibaba/Sentinel/wiki/%E6%B5%81%E9%87%8F%E6%8E%A7%E5%88%B6
     */
    private static void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
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
    private static void baseThrowException () {
        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的逻辑
                System.out.println("hello baseThrowException");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                // System.out.println("blocked!");
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
    private static void baseBooleanValue () {
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
