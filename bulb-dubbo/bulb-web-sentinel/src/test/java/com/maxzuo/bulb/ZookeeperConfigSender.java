package com.maxzuo.bulb;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 配置zookeeper动态规则
 * <p>
 * Created by zfh on 2019/10/10
 */
public class ZookeeperConfigSender {

    public static void main(String[] args) throws Exception {
        final String remoteAddress = "localhost:2181";
        final String rule = "[\n"
                + "  {\n"
                + "    \"resource\": \"com.maxzuo.controller.ShopRest.flow()\",\n"
                + "    \"controlBehavior\": 0,\n"
                + "    \"count\": 1.0,\n"
                + "    \"grade\": 1,\n"
                + "    \"limitApp\": \"default\",\n"
                + "    \"strategy\": 0\n"
                + "  }\n"
                + "]";

        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(remoteAddress, new ExponentialBackoffRetry(1000, 3));
        zkClient.start();
        String appName = "bulb-web";
        String path = "/sentinel_rule_config/" + appName + "/flow";
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        zkClient.setData().forPath(path, rule.getBytes());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        zkClient.close();
    }
}
