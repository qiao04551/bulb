package com.maxzuo.graphql.vo;

/**
 * 店铺信息 视图对象
 * <p>
 * Created by zfh on 2019/08/19
 */
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "ShopInfoVO{" +
                "shopId=" + shopId +
                ", logUrl='" + logUrl + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopDesc=" + shopDesc +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
