package com.maxzuo.dubbo.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺订单表实体
 * Created by zfh on 2019/01/18
 */
public class ShopOrderInfo implements Serializable {

    private static final long serialVersionUID = 4860493305618315471L;

    /** 主键 */
    private Integer           id;

    /**
     * 用户ID
     */
    private Integer           userId;

    /** 用户名 */
    private String            username;

    /** 数量 */
    private Integer           count;

    /** 订单编号 */
    private String            orderNo;

    /** 创建时间 */
    private Date              createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ShopOrderInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", count=" + count +
                ", orderNo='" + orderNo + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
