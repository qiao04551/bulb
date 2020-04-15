package com.maxzuo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义GatewayFilter
 * <pre>
 *   1.自定义GatewayFilter有两种实现方式，一种是直接实现GatewayFilter接口，另一种是继承AbstractGatewayFilterFactory类，任意选一种即可。
 *   2.可以使用 代码的方式 或者 配置文件的方式 配置过滤器。其中，配置文件的方式需要过滤器工厂类
 * </pre>
 * Created by zfh on 2020/04/14
 */
@Component
public class AuthorizeGatewayFilter implements GatewayFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizeGatewayFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("Authorize Filter request uri: {}", request.getURI());

        return chain.filter(exchange);
    }
}
