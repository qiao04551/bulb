package com.maxzuo.bulb.im.common;

/**
 * 通信的消息体
 * <p>
 * Created by zfh on 2019/09/22
 */
public class MessageDTO {

    private Integer userid;

    private String username;

    private Integer commandType;

    private String payLoad;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    @Override
    public String toString() {
        return "MessageEntiry{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", commandType=" + commandType +
                ", payLoad='" + payLoad + '\'' +
                '}';
    }
}
