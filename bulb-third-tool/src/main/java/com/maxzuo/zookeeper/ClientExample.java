package com.maxzuo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * zookeeper客户端示例
 * Created by zfh on 2019/03/03
 */
public class ClientExample {

    /**
     * zookeeper主机和端口
     * <pre>
     *  连接zookeeper集群：127.0.0.1:2181,127.0.0.2:2181,127.0.0.3:2181
     * </pre>
     */
    private static final String  CONNECT_STRING  = "127.0.0.1:2181";

    private static final Integer SESSION_TIMEOUT = 2000;

    private static ZooKeeper     zkClient;

    @DisplayName("初始化")
    @BeforeAll
    static void init() {
        try {
            zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    // 监听事件
                    System.out.println("listener: " + watchedEvent);
                    try {
                        List<String> children = zkClient.getChildren("/", true);
                        for (String item : children) {
                            System.out.println("ChildNode: " + item);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("创建节点")
    @Test
    void createNode() {
        try {
            String path = zkClient.create("/test", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
            System.out.println("path: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("判断节点是否存在")
    @Test
    void getChildNodeAndWatch() {
        try {
            // 参数二：是否监听
            Stat stat = zkClient.exists("/test", false);
            System.out.println(stat == null ? "not exists" : "exists");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
