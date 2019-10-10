package com.maxzuo.protect;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Sentinel使用Zookeeper配置动态规则（Push模式）
 * <p>
 * Created by zfh on 2019/07/05
 */
@Component
public class SentinelZookeeperRules {

    private static final Logger logger = LoggerFactory.getLogger(SentinelZookeeperRules.class);

    @PostConstruct
    public void loadRules() {
        logger.info("【Sentinel保护资源】开始检出zookeeper规则 ...");

        String zookeeperAddress = "127.0.0.1:2181";
        final String flowPath = "/sentinel_rule_config/bulb-web/flow";
        // 限流规则-Qos
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource2 = new ZookeeperDataSource<>(zookeeperAddress, flowPath,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource2.getProperty());

        // 降级规则-RT
        final String degradePath = "/sentinel_rule_config/bulb-web/degrade";
        ReadableDataSource<String, List<DegradeRule>> degradeDataSource = new ZookeeperDataSource<>(zookeeperAddress, degradePath,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeDataSource.getProperty());

        // 授权规则
        final String authorityPath = "/sentinel_rule_config/bulb-web/authorith";
        ReadableDataSource<String, List<AuthorityRule>> authorityDataSource = new ZookeeperDataSource<>(zookeeperAddress, authorityPath,
                source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {}));
        AuthorityRuleManager.register2Property(authorityDataSource.getProperty());
    }
}
