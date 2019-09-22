package com.maxzuo.bulb.im.common;

/**
 * 上线的服务信息
 * <p>
 * Created by zfh on 2019/09/22
 */
public class ServerOnlineInfo {

    private String ip;

    private Integer port;

    public ServerOnlineInfo() {
    }

    public ServerOnlineInfo(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerOnlineInfo{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
