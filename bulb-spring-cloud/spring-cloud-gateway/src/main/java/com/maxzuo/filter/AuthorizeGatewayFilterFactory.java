package com.maxzuo.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 自定义过滤器工厂，在配置文件中配置过滤器。
 * <pre>
 *  在配置文件中写 Authorize 而不是全部类名，最终都会相应的过滤器工厂类处理。
 * </pre>
 * Created by zfh on 2020/04/15
 */
@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    /**
     * Prefix key.
     */
    private static final String PREFIX_KEY = "prefix";

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PREFIX_KEY);
    }

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new AuthorizeGatewayFilter();
    }

    public static class Config {

        private int prefix;

        public int getPrefix() {
            return prefix;
        }

        public void setPrefix(int prefix) {
            this.prefix = prefix;
        }
    }
}
