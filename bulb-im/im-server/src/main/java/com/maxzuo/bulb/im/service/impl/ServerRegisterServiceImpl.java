package com.maxzuo.bulb.im.service.impl;

import com.maxzuo.bulb.im.constant.Const;
import com.maxzuo.bulb.im.service.IServerRegisterService;
import com.maxzuo.bulb.im.util.OSUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 服务端注册 实现类
 * <p>
 * Created by zfh on 2019/09/22
 */
@Service("serverRegisterService")
public class ServerRegisterServiceImpl implements IServerRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(ServerRegisterServiceImpl.class);

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
    public void serverOnlineRegiste (String ip, Integer port) {
        try {
            String nodeName = Const.SERVER_NODE_PREFIX + "/" + OSUtil.getProcessNo();
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(nodeName, (ip + "," + port).getBytes());
            logger.info("【服务上线节点注册信息】nodeName = {} data = {}", nodeName, ip + "," + port);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("【服务上线，节点注册失败】");
        }
    }
}
