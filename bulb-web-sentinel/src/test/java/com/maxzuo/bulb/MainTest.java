package com.maxzuo.bulb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zfh on 2019/01/21
 */
public class MainTest {

    private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void testAOP() {
        logger.info("hello");
    }
}
