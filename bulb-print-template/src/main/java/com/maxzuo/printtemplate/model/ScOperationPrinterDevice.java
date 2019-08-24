package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印机实体
 * Created by zfh on 2019/1/8
 */
public class ScOperationPrinterDevice implements Serializable {

    private static final long serialVersionUID = 3784955434189951427L;

    /** 主键 */
    private Integer id;

    /** 名称 */
    private String deviceName;

    /** 打印机类型：1-服务器 2-网络打印机 */
    private Integer printerDeviceType;

    /** ip地址 */
    private String address;

    /** 是否删除：0-未删除 1-已删除 */
    private Integer delete;

    /** 店铺id */
    private Integer shopId;

    /** 创建人 */
    private Integer creatorId;

    /** 创建人姓名 */
    private String creatorName;

    /** 更新人 */
    private Integer updatorId;

    /** 更新人姓名 */
    private String updatorName;

    /** 添加时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Integer getPrinterDeviceType() {
        return printerDeviceType;
    }

    public void setPrinterDeviceType(Integer printerDeviceType) {
        this.printerDeviceType = printerDeviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public Integer getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Integer updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName == null ? null : updatorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ScOperationPrinterDevice{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", printerDeviceType=" + printerDeviceType +
                ", address='" + address + '\'' +
                ", delete=" + delete +
                ", shopId=" + shopId +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", updatorId=" + updatorId +
                ", updatorName='" + updatorName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}