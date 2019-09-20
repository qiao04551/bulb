package com.maxzuo.socket.heartbeat;

import com.maxzuo.socket.heartbeat.client.SimpleHttpClient;
import com.maxzuo.socket.heartbeat.client.SimpleHttpRequest;
import com.maxzuo.socket.heartbeat.client.SimpleHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 简单的心跳发送器
 * <p>
 * Created by zfh on 2019/09/20
 */
public class SimpleHttpHeartbeatSender {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpHeartbeatSender.class);

    private ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(2);

    public static void main(String[] args) {
        new SimpleHttpHeartbeatSender().scheduleHeartbeatTask(5000);
    }

    private void scheduleHeartbeatTask(long interval) {
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    sendHeartbeat();
                } catch (Throwable e) {
                    logger.info("[HeartbeatSender] Send heartbeat error", e);
                }
            }
        }, 5000, interval, TimeUnit.MILLISECONDS);
        logger.info("[HeartbeatSenderInit] HeartbeatSender started: " + SimpleHttpHeartbeatSender.class.getCanonicalName());
    }

    /**
     * 发送心跳
     */
    private boolean sendHeartbeat() {
        SimpleHttpRequest request = new SimpleHttpRequest(new InetSocketAddress("127.0.0.1", 8089), "/dazuo");
        try {
            SimpleHttpResponse response = new SimpleHttpClient().get(request);
            Integer statusCode = response.getStatusCode();
            if (statusCode.equals(200)) {
                logger.info(String.valueOf(response));
                return true;
            }
        } catch (Exception e) {
            logger.warn("[SimpleHttpHeartbeatSender] request Failed", e);
        }
        return false;
    }
}
