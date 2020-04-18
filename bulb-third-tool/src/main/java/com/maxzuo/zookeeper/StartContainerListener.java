package com.maxzuo.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;

/**
 * Spring容器初始化完毕，采集应用基础信息 主机名、应用名、PID。
 * Created by zfh on 2019/03/08
 */
@Component
public class StartContainerListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger       = LoggerFactory.getLogger(StartContainerListener.class);

    private static final String FINAL_NAME   = "bulb-web";

    private static final String CONNECT_ADDR = "127.0.0.1:2181";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 防止重复执行
        if (event.getApplicationContext().getParent() == null) {
            collectAppBaseInfo();
        }
    }

    private static void collectAppBaseInfo() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            String hostname = address.getHostName();
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            Integer pid = Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
            logger.info("【Spring容器初始化监听】采集信息 hostname = {} , finalName = {} , pid = {}", hostname, FINAL_NAME, pid);
            writerToZookeeper(hostname, pid);
        } catch (Exception e) {
            logger.error("【Spring容器初始化监听】采集异常", e);
        }
    }

    /** 写入zookeeper */
    private static void writerToZookeeper(String hostname, Integer pid) {
        ZkClient zkClient = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
        String basePath = "/zxcity/apm/" + hostname;
        if (!zkClient.exists(basePath)) {
            zkClient.createPersistent(basePath, true);
        }
        String childPath = basePath + "/" + FINAL_NAME;
        if (zkClient.exists(childPath)) {
            zkClient.delete(childPath);
        }
        zkClient.createEphemeral(childPath, pid);
        if (zkClient.exists(childPath)) {
            logger.info("【Spring容器初始化监听】节点创建成功");
        } else {
            logger.info("【Spring容器初始化监听】节点创建失败");
        }

        // kill -9 无法触发钩子
        Runtime.getRuntime().addShutdownHook(new Thread("zxcity-apm-zkclient") {
            @Override
            public void run() {
                zkClient.close();
            }
        });
    }
}
