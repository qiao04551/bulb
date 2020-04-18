package com.maxzuo.vertx;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
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

            /// 简单的启动
            // server.requestHandler(request -> {
            //     HttpServerResponse response = request.response();
            //     response.putHeader("content-type", "text/plain");
            //
            //     response.end("Hello World!");
            // });

            /// 创建路由
            Router router = Router.router(vertx);
            router.route("/index").handler(request -> {
                request.response().end("hello index");
            });

            router.route("/hello").handler(request -> {
                request.response().end("hello world");
            });
            server.requestHandler(router);

            // 限制HTTP请求方法
            router.post("/post").handler(event -> {
                event.response().end("hello post");
            });
            router.get("/get").handler(request -> {
                request.response().end("hello get");
            });
            router.route(HttpMethod.GET, "/get2").handler(request -> {
                request.response().end("hello get2");
            });

            System.out.println("服务启动成功，访问地址：http://127.0.0.1:8080");
            server.listen(8080);
        } catch (Exception e) {
            logger.info("发生异常！", e);
        }
    }
}
