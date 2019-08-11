package com.maxzuo.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thrift 服务端
 * Created by zfh on 2019/08/11
 */
public class SimpleServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    public static void main(String[] args) {
        TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

        try {
            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(8090);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());

            TServer server = new TSimpleServer(tArgs);
            server.serve();
        } catch (Exception e) {
            logger.error("服务端发生异常！", e);
        }
    }
}
