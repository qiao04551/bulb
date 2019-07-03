package com.maxzuo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate请求测试
 * <p>
 * Created by zfh on 2019/07/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGet() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders headers = responseEntity.getHeaders();
        String body = responseEntity.getBody();
        logger.info("statusCode：{}", statusCode);
        logger.info("headers：{}", headers);
        logger.info("body：{}", body);
    }
}
