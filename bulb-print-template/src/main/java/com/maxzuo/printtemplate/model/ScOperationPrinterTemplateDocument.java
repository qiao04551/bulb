package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印模板-文档表
 * Created by zfh on 2018/12/12
 */
public class ScOperationPrinterTemplateDocument implements Serializable {

    private static final long serialVersionUID = 5061220291589710884L;

    /** 主键 */
    private Integer id;

    /** 模板文档名称 */
    private String name;

    /** 文档类型 */
    private Integer documentType;

    /** 模板预览图 */
    private String url;

    /** 模板状态：0-删除 1-启用 2-禁用 */
    private Integer status;

    /** 店铺id */
    private Integer shopId;

    /** 创建者id */
    private Integer creatorId;

    /** 创建者姓名 */
    private String creatorName;

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
        this.name = name;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.creatorName = creatorName;
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
        return "ScOperationPrinterTemplateDocument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentType=" + documentType +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", shopId=" + shopId +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
