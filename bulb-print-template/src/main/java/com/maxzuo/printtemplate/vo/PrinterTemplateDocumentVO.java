package com.maxzuo.printtemplate.vo;

import java.util.List;

/**
 * 打印模板列表 视图VO
 * Created by zfh on 2018/12/27
 */
public class PrinterTemplateDocumentVO {

    /** 模板id */
    private Integer id;

    /** 模板类型 */
    private Integer documentType;

    /** 模板名称 */
    private String name;

    /** 模板状态：1-启用 2-禁用 */
    private Integer status;

    /** 模板默认表示 0-否 1-是 */
    private Integer defaultTemplet;

    /** 票据预览图 */
    private String url;

    /** 模板组件 */
    private List modules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
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

    public Integer getDefaultTemplet() {
        return defaultTemplet;
    }

    public void setDefaultTemplet(Integer defaultTemplet) {
        this.defaultTemplet = defaultTemplet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List getModules() {
        return modules;
    }

    public void setModules(List modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "PrinterTemplateDocumentVO{" +
                "id=" + id +
                ", documentType=" + documentType +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", defaultTemplet=" + defaultTemplet +
                ", url='" + url + '\'' +
                ", modules='" + modules + '\'' +
                '}';
    }
}
