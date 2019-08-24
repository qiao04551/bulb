package com.maxzuo.printtemplate.vo;

import java.util.List;

/**
 * 出票口详情视图
 * Created by zfh on 2019/01/09
 */
public class PrinterKitchenIntegrationVO {

    /** 出票口ID */
    private Integer id;

    /** 出票口名称 */
    private String name;

    /** 打印机ID */
    private Integer printerDeviceId;

    /** 店铺ID */
    private Integer shopId;

    /** 出票口票据类型 */
    private List documentTypeRules;

    /** 是否配置桌台区域：0-未配置 1-配置 */
    private Integer table;

    /** 是否配置打印的菜品：0-未配置 1-配置 */
    private Integer goods;

    /** 出票口桌台区域 */
    private List tableRules;

    /** 出票口配置的商品 */
    private List goodsRules;

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
        this.name = name;
    }

    public Integer getPrinterDeviceId() {
        return printerDeviceId;
    }

    public void setPrinterDeviceId(Integer printerDeviceId) {
        this.printerDeviceId = printerDeviceId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public List getDocumentTypeRules() {
        return documentTypeRules;
    }

    public void setDocumentTypeRules(List documentTypeRules) {
        this.documentTypeRules = documentTypeRules;
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

    public List getTableRules() {
        return tableRules;
    }

    public void setTableRules(List tableRules) {
        this.tableRules = tableRules;
    }

    public List getGoodsRules() {
        return goodsRules;
    }

    public void setGoodsRules(List goodsRules) {
        this.goodsRules = goodsRules;
    }

    @Override
    public String toString() {
        return "PrinterKitchenIntegrationVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", printerDeviceId=" + printerDeviceId +
                ", shopId=" + shopId +
                ", documentTypeRules=" + documentTypeRules +
                ", table=" + table +
                ", goods=" + goods +
                ", tableRules=" + tableRules +
                ", goodsRules=" + goodsRules +
                '}';
    }
}
