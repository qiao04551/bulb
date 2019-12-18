package com.maxzuo.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * API方式是最简单、高效使用Apollo配置的方式，不依赖Spring框架即可使用
 * <p>
 * Created by zfh on 2019/12/18
 */
public class ApolloExample {

    private static final Logger logger = LoggerFactory.getLogger(ApolloExample.class);

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("app.id", "SampleApp");
        System.setProperty("apollo.meta", "http://127.0.0.1:8080");

        // 监听变更
        ConfigChangeListener changeListener = new ConfigChangeListener() {

            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                logger.info("Changes for namespace {}", changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    logger.info("Change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
                            change.getPropertyName(), change.getOldValue(), change.getNewValue(),
                            change.getChangeType());
                }
            }
        };

        Config config = ConfigService.getAppConfig();
        config.addChangeListener(changeListener);

        Integer value = config.getIntProperty("timeout", 0);
        System.out.println(value);

        TimeUnit.SECONDS.sleep(10000);
    }
}
