package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 出票口-票据类型规则实体
 * Created by zfh on 2019/1/8
 */
public class ScOperationPrinterKitchenDocumentTypeRules implements Serializable {

    private static final long serialVersionUID = -5920364244309155023L;

    /** 主键 */
    private Integer id;

    /** 出票口id */
    private Integer printerKitchenId;

    /** 票据类型id */
    private Integer documentTypeId;

    /** 打印的份数 */
    private Integer number;

    /** 打印方式：1-一纸多菜 2-一纸一菜 3-各打一张 */
    private Integer printerType;

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

    public Integer getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPrinterType() {
        return printerType;
    }

    public void setPrinterType(Integer printerType) {
        this.printerType = printerType;
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
        return "ScOperationPrinterKitchenDocumentTypeRules{" +
                "id=" + id +
                ", printerKitchenId=" + printerKitchenId +
                ", documentTypeId=" + documentTypeId +
                ", number=" + number +
                ", printerType=" + printerType +
                ", delete=" + delete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}