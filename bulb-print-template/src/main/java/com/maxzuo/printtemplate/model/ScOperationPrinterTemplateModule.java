package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印模板-模块实体
 * Created by zfh on 2018/12/12
 */
public class ScOperationPrinterTemplateModule implements Serializable {

    private static final long serialVersionUID = 8485241363055149502L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块描述
     */
    private String moduleDescribe;

    /**
     * 文档类型
     */
    private Integer documentType;

    /**
     * 模块排序
     */
    private Integer sort;

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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescribe() {
        return moduleDescribe;
    }

    public void setModuleDescribe(String moduleDescribe) {
        this.moduleDescribe = moduleDescribe;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "ScOperationPrinterTemplateModule{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", moduleDescribe='" + moduleDescribe + '\'' +
                ", documentType=" + documentType +
                ", sort=" + sort +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
