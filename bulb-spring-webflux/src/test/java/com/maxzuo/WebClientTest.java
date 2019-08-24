package com.maxzuo;

import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 使用WebClient开发响应式Http客户端
 * <p>
 * Created by zfh on 2019/06/04
 */
public class WebClientTest {

    @Test
    public void testWebClient() {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<String> resp = webClient
                .get()
                .uri("/hello")
                // 异步地获取response信息
                .retrieve()
                .bodyToMono(String.class);

        // 答应请求结果
        resp.subscribe(System.out::println);

        try {
            // 将线程sleep 1秒确保拿到response
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
