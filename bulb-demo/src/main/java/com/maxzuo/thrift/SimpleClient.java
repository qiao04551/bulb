package com.maxzuo.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thrift 客户端
 * <p>
 * Created by zfh on 2019/08/11
 */
public class SimpleClient {

    private static final Logger logger = LoggerFactory.getLogger(SimpleClient.class);

    public static void main(String[] args) {
        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 8090, 10);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            transport.open();
            String result = client.sayHello("dazuo");

            System.out.println("Thrify client result =: " + result);
        } catch (Exception e) {
            logger.error("客户端发生异常！", e);
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
