package com.maxzuo.grpc;

import com.maxzuo.grpc.service.SearchServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * GRPC 服务端
 * <p>
 * Created by zfh on 2019/08/23
 */
public class GrpcServer {

    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);

    private int port = 5000;

    private Server server;

    public static void main(String[] args) {
        GrpcServer server = new GrpcServer();
        try {
            server.start();
            server.blockUntilShutdown();
        } catch (Exception e) {
            logger.error("GRC 服务端发生异常！", e);
        }
    }

    public void start () throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new SearchServiceImpl())
                .build()
                .start();

        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.error("shutting down gRPC server since JVM is shutting down");
                GrpcServer.this.stop();
                logger.error("server shut down");
            }
        });
    }

    private void stop () {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * 等待主线程上的终止，因为grpc库使用守护进程线程。
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
