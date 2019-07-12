package com.maxzuo.protect;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Zookeeper config sender for demo
 *
 * @author guonanjun
 */
public class SentinelZookeeperConfigSender {

    private static final int RETRY_TIMES = 3;

    private static final int SLEEP_TIME = 1000;

    private static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";

    private static final String FLOW_RULE_PATH = "/sentinel_rules/%s/flow";

    private static final String DEGRADE_RULE_PATH = "/sentinel_rules/%s/degrade";

    public static void main(String[] args) throws Exception {
        String appName = "bulb-web";

        // 限流规则-Qos
        String flowPath = String.format(FLOW_RULE_PATH, appName);
        final String flowRule = "[\n"
                + "  {\n"
                + "    \"resource\": \"com.maxzuo.controller.Rest.flow(java.lang.String,java.lang.Integer)\",\n"
                + "    \"controlBehavior\": 0,\n"
                + "    \"count\": 2,\n"
                + "    \"grade\": 1,\n"
                + "    \"limitApp\": \"default\",\n"
                + "    \"strategy\": 0\n"
                + "  }"
                + "]";

        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(ZOOKEEPER_ADDRESS, new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
        zkClient.start();
        Stat stat = zkClient.checkExists().forPath(flowPath);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(flowPath, null);
        }
        zkClient.setData().forPath(flowPath, flowRule.getBytes());

        // 降级规则-RT
        String degradePath = String.format(DEGRADE_RULE_PATH, appName);
        final String degradeRule = "[\n"
                + "  {\n"
                + "    \"resource\": \"com.maxzuo.controller.Rest.degrade()\",\n"
                + "    \"count\": 1,\n"
                + "    \"grade\": 0,\n"
                + "    \"timeWindow\": 10,\n"
                + "    \"limitApp\": \"default\",\n"
                + "    \"strategy\": 0\n"
                + "  }\n"
                + "]";

        Stat stat2 = zkClient.checkExists().forPath(degradePath);
        if (stat2 == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(degradePath, null);
        }
        zkClient.setData().forPath(degradePath, degradeRule.getBytes());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        zkClient.close();
    }
}
