package com.maxzuo.model;

/**
 * 心跳数据载体
 * <p>
 * Created by zfh on 2019/11/29
 */
public class HealthMessageDTO extends MessageDTO {

    /**
     * 消息负载
     */
    private String payLoad;

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    @Override
    public String toString() {
        return "PingPongMessageDTO{" +
                "payLoad='" + payLoad + '\'' +
                '}';
    }
}
