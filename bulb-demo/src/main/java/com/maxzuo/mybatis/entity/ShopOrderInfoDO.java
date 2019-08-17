package com.maxzuo.mybatis.entity;

import lombok.Data;

import java.util.Date;

/**
 * 店铺订单表实体
 * Created by zfh on 2019/01/18
 */
@Data
public class ShopOrderInfoDO {

    private Integer           id;

    private Integer           userId;

    private String            username;

    private Integer           count;

    private String            orderNo;

    private Date              createTime;
}
