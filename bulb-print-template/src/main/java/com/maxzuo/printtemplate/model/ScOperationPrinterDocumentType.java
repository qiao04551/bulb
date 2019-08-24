package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印文档类型实体
 * Created by zfh on 2018/12/11
 */
public class ScOperationPrinterDocumentType implements Serializable {

    private static final long serialVersionUID = 2779853373257838652L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 状态：0-禁用 1-启用
     */
    private Integer status;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "ScOperationPrinterDocumentType{" +
                "id=" + id +
                ", name=" + name +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
