package com.maxzuo.elastic.example;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * 测试类基类（TransportClient，基于Tcp协议）
 * <p>
 * Created by zfh on 2019/09/17
 */
public class ElasticApiBase {

    private static final Logger logger = LoggerFactory.getLogger(DocumentApiExample.class);

    private static final String ES_IP = "192.168.1.1";

    private static final Integer ES_PROT = 9300;

    protected static TransportClient client;

    static {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "my-es-cluster")
                /*
                    设置client.transport.sniff为 true 来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中。
                    这样做的好处是，一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器。

                    注意：当ES服务器监听（publish_address ）使用内网服务器IP，而访问（bound_addresses ）使用外网IP时，不要
                    使用 client.transport.sniff为true，在自动发现时会使用内网IP进行通信，导致无法连接到ES服务器。因此此时需
                    要将节点手动添加到client中。（默认为 false）
                 */
                .put("client.transport.sniff", false)
                .build();
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_IP), ES_PROT));
        } catch (Exception e) {
            logger.error("ES连接异常", e);
        }
    }
}
