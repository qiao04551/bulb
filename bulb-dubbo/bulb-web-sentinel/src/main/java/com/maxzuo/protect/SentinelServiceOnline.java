package com.maxzuo.protect;

import com.alibaba.fastjson.JSONObject;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 模拟服务上线
 * <p>
 * Created by zfh on 2019/07/19
 */
public class SentinelServiceOnline {

    private static final Logger logger = LoggerFactory.getLogger(SentinelServiceOnline.class);

    private static final String ONLINE_APP_PATH = "/sentinel/online_app/%s/%s";

    public static void main(String[] args) {
        String zookeeperAddress = "127.0.0.1:2181";

        String env = "37test";

        String finalName = "bulb-web";

        new SentinelServiceOnline().serviceOnline(zookeeperAddress, env, finalName);
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            serviceInfo.put("finalName", "bulb-web");
            serviceInfo.put("component", "SpringBoot");
            serviceInfo.put("onlineTime", onlineTime);
            nodeData = JSONObject.toJSONString(serviceInfo).getBytes();
        } catch (Exception e) {
            logger.info("【Zookeeper服务上线】获取服务信息异常！", e);
        }
        return nodeData;
    }
}
