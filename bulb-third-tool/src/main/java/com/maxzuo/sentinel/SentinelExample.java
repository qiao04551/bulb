package com.maxzuo.sentinel;

import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Sentinel 服务保护
 * <p>
 * Created by zfh on 2019/09/27
 */
public class SentinelExample {

    private static final Logger logger = LoggerFactory.getLogger(SentinelExample.class);

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        String resourceName = "res";
        initFlowQpsRule(resourceName);

        for (; ; ) {
            Thread.sleep(10);
            /// 同步调用
            // defineResource(resourceName);

            // pool.execute(new Runnable() {
            //     @Override
            //     public void run() {
            //         defineResource(resourceName);
            //     }
            // });

            /// 异步调用
            asyncInvokeResource(resourceName);
        }
    }

    /**
     * 定义资源
     */
    private static void defineResource(String resourceName) {
        try (Entry ignored = SphU.entry(resourceName)) {

            System.out.println("hello world!" + Thread.currentThread().getId());

        } catch (BlockException e) {
            logger.info("BlockException！");
        }
    }

    /**
     * 定义流控规则（QPS）
     */
    private static void initFlowQpsRule(String resourceName) {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule(resourceName);
        // set limit qps to 20
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 异步调用
     */
    private static void asyncInvokeResource(String resourceName) {
        try {
            AsyncEntry entry = SphU.asyncEntry(resourceName);

            pool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello async!");
                    entry.exit();
                }
            });
        } catch (BlockException e) {
            logger.info("BlockException!");
        }
    }
}
