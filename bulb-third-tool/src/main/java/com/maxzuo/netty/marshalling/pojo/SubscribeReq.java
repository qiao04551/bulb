package com.maxzuo.netty.marshalling.pojo;

import java.io.Serializable;

/**
 * Created by zfh on 2020/01/05
 */
public class SubscribeReq implements Serializable {

    private static final long serialVersionUID = 5388498373958648845L;

    private Integer subReqID;

    private String userName;

    private String productName;

    public Integer getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(Integer subReqID) {
        this.subReqID = subReqID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "SubscribeReq{" +
                "subReqID=" + subReqID +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
