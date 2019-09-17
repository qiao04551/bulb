package com.maxzuo.elastic.example;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.junit.Test;

/**
 * Elasticsearch提供了一个完整的Java API来处理管理任务。
 * <p>
 * Created by zfh on 2019/09/17
 */
public class AdminApiExample extends ElasticApiBase {

    /**
     * 创建索引
     */
    @Test
    public void testCteateIndex () {
        AdminClient admin = client.admin();
        admin.indices()
                .prepareCreate("test_dz")
                .get();
    }

    /**
     * 集群API
     */
    @Test
    public void testClusterHealth () {
        ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
        String clusterName = healths.getClusterName();
        int numberOfDataNodes = healths.getNumberOfDataNodes();
        int numberOfNodes = healths.getNumberOfNodes();
        for (ClusterIndexHealth health : healths.getIndices().values()) {
            String index = health.getIndex();
            int numberOfShards = health.getNumberOfShards();
            int numberOfReplicas = health.getNumberOfReplicas();
            ClusterHealthStatus status = health.getStatus();
            System.out.println("index: " + index);
            System.out.println("numberOfShards: " + numberOfShards);
            System.out.println("numberOfReplicas: " + numberOfReplicas);
            System.out.println("status: " + status);
        }
        System.out.println("clusterName: " + clusterName);
        System.out.println("numberOfDataNodes: " + numberOfDataNodes);
        System.out.println("numberOfNodes: " + numberOfNodes);

    }
}
