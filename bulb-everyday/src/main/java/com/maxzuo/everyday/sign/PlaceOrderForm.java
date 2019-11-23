package com.maxzuo.everyday.sign;

/**
 * 模拟Web请求对象
 * <p>
 * Created by zfh on 2019/11/23
 */
public class PlaceOrderForm {

    private String appid;

    private Integer totalAmount;

    private String body;

    private String detail;

    private String nonceStr;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
