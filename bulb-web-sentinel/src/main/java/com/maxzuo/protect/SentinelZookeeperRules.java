package com.maxzuo.protect;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sentinel使用Zookeeper配置动态规则（Push模式）
 * <p>
 * Created by zfh on 2019/07/05
 */
@Component
public class SentinelZookeeperRules {

    private static final Logger logger = LoggerFactory.getLogger(SentinelZookeeperRules.class);

    private static final String FLOW_RULE_PATH = "/sentinel_rules/%s/%s/flow";

    private static final String DEGRADE_RULE_PATH = "/sentinel_rules/%s/%s/degrade";

    private static final String ONLINE_APP_PATH = "/sentinel/online_app/%s/%s";

    @PostConstruct
    public void init() {
        String zookeeperAddress = "127.0.0.1:2181";

        String env = "37test";

        String finalName = "restful";

        System.setProperty("env", env);

        System.out.println("【Sentinel保护资源】开始检出zookeeper规则 ...");
        // 限流规则-Qos
        String flowPath2 = String.format(FLOW_RULE_PATH, env, finalName);
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource2 = new ZookeeperDataSource<>(zookeeperAddress, flowPath2,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource2.getProperty());

        // 降级规则-RT
        String degradePath = String.format(DEGRADE_RULE_PATH, env, finalName);
        ReadableDataSource<String, List<DegradeRule>> degradeDataSource = new ZookeeperDataSource<>(zookeeperAddress, degradePath,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeDataSource.getProperty());

        serviceOnline(zookeeperAddress, env, finalName);
    }

    /**
     * 服务上线
     * @param zkAddress 地址
     * @param env       环境
     * @param finalName 应用
     */
    private void serviceOnline (String zkAddress, String env, String finalName) {
        String nodePath = String.format(ONLINE_APP_PATH, env, finalName);
        logger.info("【Zookeeper服务上线】nodePath = {}", nodePath);
        try {
            CuratorFramework zkClient = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(3, 1000));
            zkClient.start();

            if (zkClient.checkExists().forPath(nodePath) == null) {
                zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(nodePath, getServiceInfo());
            }
            /// 断开连接后，节点被删除
            // zkClient.close();
        } catch (Exception e) {
            logger.error("【Zookeeper服务上线】发生异常！", e);
        }
    }

    /**
     * 服务的信息
     */
    private byte[] getServiceInfo () {
        byte[] nodeData = new byte[0];
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            Integer pid = Integer.valueOf(runtimeMXBean.getName().split("@")[0]);

            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
            String onlineTime = sdf.format(new Date());

            Map<String, String> serviceInfo = new HashMap<>(10);
            serviceInfo.put("address", hostAddress + ":" + pid);
            serviceInfo.put("finalName", "restful");
            serviceInfo.put("component", "SpringMVC");
            serviceInfo.put("onlineTime", onlineTime);
            nodeData = JSONObject.toJSONString(serviceInfo).getBytes();
        } catch (Exception e) {
            logger.info("【Zookeeper服务上线】获取服务信息异常！", e);
        }
        return nodeData;
    }
}
