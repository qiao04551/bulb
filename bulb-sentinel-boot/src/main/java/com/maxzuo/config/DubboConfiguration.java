package com.maxzuo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring-Boot整合Dubbo
 * <p>
 * Created by zfh on 2019/06/02
 */
@Configuration
@ImportResource({"classpath:dubbo-consumer.xml"})
public class DubboConfiguration {

}
