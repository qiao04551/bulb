package com.maxzuo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义GlobalFilter
 * <pre>
 *   1.只需要添加 @Component 注解，不需要进行任何额外的配置，实现GlobalFilter接口，自动会对所有的路由起作用
 *   2.可以实现Order接口或者在类上添加@Order注解指定运行的优先级
 *     较高的值被解释为较低的优先级。因此，具有最低值的对象具有最高优先级(有点类似于Servlet加载-启动值)。
 *     相同的顺序值将导致受影响对象的任意排序位置。
 * </pre>
 * Created by zfh on 2020/04/14
 */
@Component
public class TokenGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(TokenGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        logger.info("Token filter request uri: {}", request.getURI());

        /// 全局限流
        // ServerHttpResponse response = exchange.getResponse();
        // response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        // return response.setComplete();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
