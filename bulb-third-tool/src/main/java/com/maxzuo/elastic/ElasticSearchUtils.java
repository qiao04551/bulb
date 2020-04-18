package com.maxzuo.elastic;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * ES工具类
 * Created by zfh on 2019/01/24
 */
public class ElasticSearchUtils {

    private static final Logger    logger  = LoggerFactory.getLogger(ElasticSearchUtils.class);

    private static final String    ES_IP   = "47.98.199.80";

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
     * @param index 索引
     * @param type 类型
     * @param id 记录ID
     * @return {@link GetResponse}
     */
    public static GetResponse getRecordByPrimaryKey(String index, String type, String id) {
        GetResponse response = null;
        if (client != null) {
            response = client.prepareGet(index, type, id).get();
        }
        return response;
    }

    /**
     * 保存一条记录
     * @param index 索引名
     * @param type  类型
     * @param document 文档
     * @return true or false
     */
    public static Boolean saveRecord(String index, String type, String document) {
        if (client != null) {
            return client.prepareIndex(index, type).setSource(document).get().isCreated();
        }
        return false;
    }

    /** 关闭ES连接 */
    public static void close() {
        if (client != null) {
            client.close();
        }
    }
}
