package com.maxzuo.bulb.im.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by zfh on 2019/09/22
 */
@Component
@PropertySource(value = "classpath:application.yml", factory = YamlResourceFactory.class)
public class AppConfiguration {

    @Value("${heartbeat.time}")
    private Integer heartbeatTime;

    public Integer getHeartbeatTime() {
        return heartbeatTime;
    }
}
