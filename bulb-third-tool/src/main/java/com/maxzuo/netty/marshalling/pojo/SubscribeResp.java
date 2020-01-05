package com.maxzuo.netty.marshalling.pojo;

import java.io.Serializable;

/**
 * Created by zfh on 2020/01/05
 */
public class SubscribeResp implements Serializable {

    private static final long serialVersionUID = 7064351952393739316L;

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
