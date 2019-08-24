package com.maxzuo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * 统一存放处理时间的Handler类
 * <pre>
 *  WebFlux的函数式开发模式
 *    HandlerFunction：相当于Controller中的具体处理方法，输入为请求，输出为装在Mono中的响应。
 *    RouterFunction：相当于@RequestMapping，用来判断什么样的url映射到那个具体的HandlerFunction，
 *    输入为请求，输出为装在Mono里边的Handlerfunction
 * </pre>
 * <p>
 * Created by zfh on 2019/06/04
 */
@Component
public class TimeHandler {

    /**
     * 获取时间
     * @param serverRequest {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
    }

    /**
     * 获取日期
     * @param serverRequest {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> getDate(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Today is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())), String.class);
    }

    /**
     * 服务器推送（每秒推送一次）
     * @param serverRequest {@link ServerRequest}
     * @return {@link Mono<ServerResponse>}
     */
    public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(  // 1
                Flux.interval(Duration.ofSeconds(1)).   // 2
                        map(l -> new SimpleDateFormat("HH:mm:ss").format(new Date())),
                String.class);
    }
}
