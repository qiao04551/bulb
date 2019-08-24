package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 出票口配置打印菜品实体
 * Created by zfh on 2019/1/8
 */
public class ScOperationPrinterKitchenGoodsRules implements Serializable {

    private static final long serialVersionUID = -3530065658560092393L;

    /** 主键 */
    private Integer id;

    /** 出票口id */
    private Integer printerKitchenId;

    /** 商品id */
    private Integer goodsId;

    /** 商品规格 */
    private Integer stockId;

    /** 是否删除：0-未删除 1-删除 */
    private Integer delete;

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

    public Integer getPrinterKitchenId() {
        return printerKitchenId;
    }

    public void setPrinterKitchenId(Integer printerKitchenId) {
        this.printerKitchenId = printerKitchenId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
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
        return "ScOperationPrinterKitchenGoodsRules{" +
                "id=" + id +
                ", printerKitchenId=" + printerKitchenId +
                ", goodsId=" + goodsId +
                ", stockId=" + stockId +
                ", delete=" + delete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}