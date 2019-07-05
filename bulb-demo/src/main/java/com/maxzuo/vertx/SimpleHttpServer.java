package com.maxzuo.vertx;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Vertx搭建简易的http服务器
 * <p>
 * Created by zfh on 2019/07/05
 */
public class SimpleHttpServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);

    public static void main(String[] args) {
        try {
            Vertx vertx = Vertx.vertx();
            HttpServer server = vertx.createHttpServer();

            server.requestHandler(request -> {
                HttpServerResponse response = request.response();
                response.putHeader("content-type", "text/plain");

                response.end("Hello World!");
            });
            System.out.println("服务启动成功，访问地址：http://127.0.0.1:8080");
            server.listen(8080);
        } catch (Exception e) {
            logger.info("发生异常！", e);
        }
    }
}
