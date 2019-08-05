package com.maxzuo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用Curator framework客户端
 * <p>
 * Created by zfh on 2019/08/05
 */
class CuratorExample {

    private CuratorFramework zkClient;

    @BeforeEach
    void createZkclient () {
        // 重连策略
        zkClient = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(3, 1000));
        zkClient.start();
    }

    @DisplayName("创建临时节点")
    @Test
    void createNode () {
        try {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/dazuo/info", "hello".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("查询节点")
    @Test
    void queryNode () {
        try {
            Stat stat = zkClient.checkExists().forPath("/dazuo");
            System.out.println("stat: " + stat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("更新节点数据")
    @Test
    void updateNodeData () {
        try {
            // zkClient.setData().forPath("/dazuo", "12345".getBytes());

            // CAS版本（dataVersion）
            zkClient.setData().withVersion(1).forPath("/dazuo", "12345".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("删除节点")
    @Test
    void deleteNode () {
        try {
            // zkClient.delete().forPath("/dazuo");

            // CAS版本（dataVersion）
            zkClient.delete().withVersion(1).forPath("/dazuo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("命名空间")
    @Test
    void namespace () {
        try {
            // 可以使用命名空间Namespace避免多个应用的节点的名称冲突。 CuratorFramework提供了命名空间的概念，
            // 这样CuratorFramework会为它的API调用的path加上命名空间
            CuratorFramework myappZkClient = zkClient.usingNamespace("myapp");

            // 在命名空间下的操作
            myappZkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/test", "hello".getBytes());
            String namespace = myappZkClient.getNamespace();
            System.out.println("namespace: " + namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("临时客户端")
    @Test
    void buildTempClient () {
        CuratorFrameworkFactory
                .builder()
                .connectString("127.0.0.1:2181")
                // 重试策略
                .retryPolicy(new ExponentialBackoffRetry(3, 1000))
                // 连接超时
                .connectionTimeoutMs(100)
                // 会话超时
                .sessionTimeoutMs(100)
                // 临时客户端并设置连接时间（超过时间连接就会关闭）
                .buildTemp(100, TimeUnit.SECONDS);
    }

    @DisplayName("监听节点数据的变化")
    @Test
    void watchNodeData () {
        /*
            Zookeeper原生支持通过注册Watcher来进行事件监听，但是其使用不是很方便。Curator引入了Cache来实现对Zookeeper服务端事件
            的监听，Cache是Curator中对事件监听的包装。
         */
        NodeCache nodeCache = new NodeCache(zkClient, "/dazuo");
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Node data update, new Data: " + new String(nodeCache.getCurrentData().getData()));
            }
        });
        try {
            nodeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("监听子节点的变化")
    @Test
    void watchChildRenCache () {
        // 参数三：用于配置是否把节点的内容缓存起来，如果配置为true，那么客户端在接收到节点列表变更的同时，也能够获取到节点的数据内容；
        // 如果配置为false，则无法获取到节点的数据内容。
        PathChildrenCache cache = new PathChildrenCache(zkClient, "/dazuo", true);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("event Type: " + event.getType() + " event Data：" + event.getData());
            }
        });
        try {
            cache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("异步接口")
    @Test
    void sync () {
        try {
            zkClient.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .inBackground(new BackgroundCallback() {
                @Override
                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                    // 方法回调
                    System.out.println("event Type: " + event.getType());
                }
            }).forPath("/dazuo", "hello".getBytes());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Master 选举")
    @Test
    void recipesMasterSelect () {
        /*
            在分布式系统中，对于一个复杂的任务，仅需要从集群中选举出一台进行处理即可。诸如此类的分布式问题，称之为Master选举。
            借助Zookeeper，我们可以很方便的实现master选举的功能，其大体思路如下：

            选择一个根节点，例如/master_select，多台机器同时向该节点创建一个子节点/master_select/lock，利用Zookeeper的特性，
            最终只有一台机器能够创建成功，成功的那台机器作为master。
         */
        String masterPath = "/master_path";
        LeaderSelector selector = new LeaderSelector(zkClient, masterPath, new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成功了master角色！");
                Thread.sleep(3000);
                System.out.println("完成master操作，释放Master权利");
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {

            }
        });
        /*
            节点在加入选举以后，除非程序结束或者close()退出选举，否则加点自加入选举以后将持续持有或者保持对主节点的竞争。

            recipes的另外一个实现Leader Election则不同，被选为主节点的节点任务如果执行完就会放弃主节点，然后由剩下的节点进行主节点竞争。
            如果你希望已经执行完的主节点再次加入主节点选举那么你需要调用autoRequeue()方法去自动加入。
         */
        // selector.autoRequeue();
        selector.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("分布式锁")
    @Test
    void recipesLock () {
        /*
            在分布式环境中，为了保证数据的一致性，经常在程序的某个运行点（例如，减库存操作或流水号生成等）需要进行同步控制。
         */
        // 互斥锁
        InterProcessMutex mutex = new InterProcessMutex(zkClient, "/lockPath");

        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @DisplayName("分布式计数器")
    @Test
    void recipesDistAtomicInt () {
        DistributedAtomicInteger count = new DistributedAtomicInteger(zkClient, "/distatomicint_path", new RetryNTimes(3, 1000));
        try {
            AtomicValue<Integer> rc = count.add(2);
            System.out.println("success: " + rc.succeeded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("分布式Barrier")
    @Test
    void recipesCyclicBarrier () {
        AtomicInteger count = new AtomicInteger(1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 16; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        CuratorFramework zkClient = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(3, 1000));
                        zkClient.start();

                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(zkClient, "/barrier_path", 5);
                        Thread.sleep(count.getAndIncrement() * 1000);

                        barrier.enter();
                        System.out.println("hello world!");
                        barrier.leave();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
