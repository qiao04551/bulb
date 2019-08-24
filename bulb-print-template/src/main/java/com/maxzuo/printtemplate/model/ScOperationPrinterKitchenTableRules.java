package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 出票口配置打印菜品实体
 * Created by zfh on 2019/1/9
 */
public class ScOperationPrinterKitchenTableRules implements Serializable {

    private static final long serialVersionUID = -8771246143755692032L;

    /** 主键 */
    private Integer id;

    /** 出票口 */
    private Integer printerKitchenId;

    /** 桌台号 */
    private Integer tableId;

    /** 是否删除 */
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

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
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
        return "ScOperationPrinterKitchenTableRules{" +
                "id=" + id +
                ", printerKitchenId=" + printerKitchenId +
                ", tableId=" + tableId +
                ", delete=" + delete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}