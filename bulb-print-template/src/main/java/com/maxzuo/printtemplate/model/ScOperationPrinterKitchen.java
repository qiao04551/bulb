package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 出票口实体
 * Created by zfh on 2019/1/8
 */
public class ScOperationPrinterKitchen implements Serializable {

    private static final long serialVersionUID = 4609560622003259131L;

    /** 主键 */
    private Integer id;

    /** 出票口名称 */
    private String name;

    /** 打印机id */
    private Integer printerDeviceId;

    /** 是否配置桌台区域：0-未配置 1-配置 */
    private Integer table;

    /** 是否配置打印的菜品：0-未配置 1-配置 */
    private Integer goods;

    /** 是否删除：0-未删除 1-删除 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPrinterDeviceId() {
        return printerDeviceId;
    }

    public void setPrinterDeviceId(Integer printerDeviceId) {
        this.printerDeviceId = printerDeviceId;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public Integer getGoods() {
        return goods;
    }

    public void setGoods(Integer goods) {
        this.goods = goods;
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
        return "ScOperationPrinterKitchen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", printerDeviceId=" + printerDeviceId +
                ", table=" + table +
                ", goods=" + goods +
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