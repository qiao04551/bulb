package com.maxzuo.bulb.im.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zfh on 2019/09/22
 */
@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlResourceFactory.class)
public class AppConfiguration {

}
