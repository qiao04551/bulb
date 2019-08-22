package com.maxzuo.graphql.vo;

import lombok.Data;

/**
 * 店铺信息 视图对象
 * <p>
 * Created by zfh on 2019/08/19
 */
@Data
public class ShopInfoVO {

    /**
     * 店铺ID
     */
    private Integer shopId;

    /**
     * Logo
     */
    private String logUrl;

    /**
     * 名称
     */
    private String shopName;

    /**
     * 简介
     */
    private String shopDesc;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;
}
