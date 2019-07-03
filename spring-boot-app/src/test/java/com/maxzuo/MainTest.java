package com.maxzuo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zfh on 2019/02/01
 */
@DisplayName("测试主类")
class MainTest {

    private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Test
    void testLog() {
        logger.info("hello world");
    }
}
