package com.maxzuo.elastic;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.elastic.model.Person;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * ES工具类 集群环境
 * Created by zfh on 2019/01/24
 */
public class ElasticSearchClusterExample {

    private static final Logger    logger  = LoggerFactory.getLogger(ElasticSearchClusterExample.class);

    private static final String    ES_IP   = "192.168.3.186";

    private static final Integer   ES_PROT = 9300;

    private static TransportClient client;

    static {
        Settings settings = Settings.settingsBuilder().put("client.transport.ignore_cluster_name", true)
            .put("client.transport.sniff", true).build();
        try {
            client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_IP), ES_PROT));
        } catch (Exception e) {
            logger.error("ES连接异常", e);
        }
    }

    /**
     * 通过ID查询数据
     */
    @Test
    public void testGetRecordByPrimaryKey() {
        String index = "test_zfh";
        String type = "file";
        String id = "AWlq3_I-P9zaf8kQeL0a";
        client.prepareGet(index, type, id).get();
    }

    /**
     * 保存一条记录
     */
    @Test
    public void testSaveRecord() {
        String index = "test_zfh";
        String type = "file";
        String document = JSONObject.toJSONString(new Person("dazuo", "java工程师"));
        client.prepareIndex(index, type).setSource(document).get().isCreated();
    }
}
