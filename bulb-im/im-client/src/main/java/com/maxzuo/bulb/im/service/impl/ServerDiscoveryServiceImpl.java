package com.maxzuo.bulb.im.service.impl;

import com.maxzuo.bulb.im.common.ServerOnlineInfo;
import com.maxzuo.bulb.im.constant.Const;
import com.maxzuo.bulb.im.service.IServerDiscoveryService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * 服务发现Server
 * <p>
 * Created by zfh on 2019/09/22
 */
@Service("serverDiscoveryService")
public class ServerDiscoveryServiceImpl implements IServerDiscoveryService {

    private static final Logger logger = LoggerFactory.getLogger(ServerDiscoveryServiceImpl.class);

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    private CuratorFramework zkClient;

    @PostConstruct
    public void init () {
        zkClient = CuratorFrameworkFactory
                .newClient(zookeeperAddress, new ExponentialBackoffRetry(3, 1000));
        zkClient.start();
    }

    @Override
    public ServerOnlineInfo obtainServerInfo() {
        try {
            List<String> childNodes = zkClient.getChildren().forPath(Const.SERVER_NODE_PREFIX);
            // TODO: 直接取第一个节点，后期改造为负载均衡
            if (childNodes.size() > 0) {
                byte[] bytes = zkClient.getData().forPath(Const.SERVER_NODE_PREFIX + "/" + childNodes.get(0));
                String[] nodeInfo = new String(bytes).split(",");
                logger.info("【服务器节点信息】{}", Arrays.toString(nodeInfo));
                return new ServerOnlineInfo(nodeInfo[0], Integer.valueOf(nodeInfo[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("服务发现，查询节点异常！");
    }
}
